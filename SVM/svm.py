# -*- coding: utf-8 -*-
"""
Spyder Editor

"""

import pandas as pd
import matplotlib as plt
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC
from sklearn.metrics import classification_report,confusion_matrix
from sklearn.metrics import accuracy_score
from sklearn_porter import Porter


LABELS = ['Downstairs',
          'LiftDown',
          'Upstairs',
          'LiftUp']


df=pd.read_csv('C:\Users\Mehedi\CSE299-Group04-Lift-and-Staircase-Detection\Datasets\\final_dataset.csv')
df

df.columns=['activity','time','Sensor','X','Y','Z']

df=df.sample(frac=1)

x=df.drop('activity',axis=1)
y=df['activity']
x=x.drop('Sensor',axis=1)
x=x.drop('time',axis=1)

x_train,x_test,y_train,y_test=train_test_split(x,y,test_size=0.20)
svclassifier=SVC(kernel='rbf',C=50,gamma=1)
svclassifier.fit(x_train,y_train)
y_pred=svclassifier.predict(x_test)
y_pred_train=svclassifier.predict(x_train)
print(confusion_matrix(y_test,y_pred))
print(classification_report(y_test,y_pred))
print(classification_report(y_train,y_pred_train))

print("Train accuracy: " +str(accuracy_score(y_train,y_pred_train)))
print ("Test accuracy: " +str(accuracy_score(y_test,y_pred)))


porter=Porter(svclassifier,language='java')
output=porter.export(embed_data=True)
print(output)
