# Random Forest Regression

import matplotlib.pyplot as plt
# Importing the libraries
import numpy as np
import pandas as pd

# Importing the dataset
dataset = pd.read_csv('Position_Salaries.csv')
X = dataset.iloc[:, 1:2].values
y = dataset.iloc[:, 2].values

# Splitting the dataset into the Training set and Test set
"""from sklearn.cross_validation import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 0)"""

# Feature Scaling
"""from sklearn.preprocessing import StandardScaler
sc_X = StandardScaler()
X_train = sc_X.fit_transform(X_train)
X_test = sc_X.transform(X_test)
sc_y = StandardScaler()
y_train = sc_y.fit_transform(y_train)"""

# Fitting Random Forest Regression to the dataset
from sklearn.ensemble import RandomForestRegressor

regressor = RandomForestRegressor(n_estimators=10, random_state=0)
regressor.fit(X, y)

# Predicting a new result
y_pred = regressor.predict([[6.1]])

from sklearn2pmml.pipeline import PMMLPipeline

pipeline = PMMLPipeline([
    ("regressor_pmml", regressor)
])
pipeline.fit(X, y)


# import pickle
#
# pickleId = pickle.dump(regressor,open( "model.p", "wb" ))

import Pyro4

# add pickle serializer to pyro
# export PYRO_SERIALIZERS_ACCEPTED=serpent,json,marshal,pickle,dill
# start name server pyro4-ns
daemon = Pyro4.Daemon()

import numpy
@Pyro4.expose
class NabeeModel(RandomForestRegressor):
    def __init__(self, obj):
        self._wrapped_obj = obj
    def __getattr__(self, attr):
        if attr in self.__dict__:
            return getattr(self, attr)
        return getattr(self._wrapped_obj, attr)
    def predict(self, X):
        print(X)
        value = numpy.array(X)
        value = value.reshape(1, -1)
        print(value)
        output = self._wrapped_obj.predict(value)
        print('output: ', output)
        return  output.tolist()

nabeeModel = NabeeModel(regressor)
nabeeModel.fit(X,y)
nabeeModel.predict([[6.1]])

daemon = daemon.serveSimple({nabeeModel: "NabeeModel"})

print(daemon)

daemon.requestLoop()


from sklearn2pmml import sklearn2pmml

sklearn2pmml(pipeline, "RandomForestRegressor.pmml", with_repr=True)

print("++++++", y_pred)

# Visualising the Random Forest Regression results (higher resolution)
X_grid = np.arange(min(X), max(X), 0.01)
X_grid = X_grid.reshape((len(X_grid), 1))
plt.scatter(X, y, color='red')
plt.plot(X_grid, regressor.predict(X_grid), color='blue')
plt.title('Truth or Bluff (Random Forest Regression)')
plt.xlabel('Position level')
plt.ylabel('Salary')
plt.show()
