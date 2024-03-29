# Random Forest Regression

# Importing the libraries
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

import Pyro4

# add pickle serializer to pyro
# export PYRO_SERIALIZERS_ACCEPTED=serpent,json,marshal,pickle,dill
# start name server >  pyro4-ns
daemon = Pyro4.Daemon()

import numpy


@Pyro4.expose
class NabeeModel(RandomForestRegressor):
    features = []

    def __init__(self, obj, features):
        self._wrapped_obj = obj
        self.features = features

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
        return output.tolist()

    def get_features(self):
        print(self.features)
        return self.features


nabeeModel = NabeeModel(regressor, ['level'])
nabeeModel.fit(X, y)
outcome = nabeeModel.predict([[6]])

# IF we wish to add object from python to hazelcast
# import hazelcast
#
# hz = hazelcast.HazelcastClient()
#
# map = hz.get_map("my-map")
#
# map.put("nabee_model",nabeeModel)


print(outcome)

# simulate pickling
import pickle

pickle.dump(nabeeModel, open("nabee_model.p", "wb"))
file = open("nabee_model.p", "rb")

nabeeModel_pickle = pickle.load(file)

daemon = daemon.serveSimple({nabeeModel_pickle: "NabeeModel"})

file.close()

print(daemon)

daemon.requestLoop()
