import pandas as pd
import matplotlib as plt


df=pd.read_csv('C:\\Users\\Mehedi\\PycharmProjects\\datasets\\lift_up\\up10.csv',delimiter=';')

df

df.columns=['time','Sensor','X','Y','Z','drop1','drop2','drop3']

df1=df.drop(['drop1','drop2','drop3'],axis=1)


search_value1=['ACC']
search_value2=['GYRO']

df2=df1[df1.Sensor.str.contains('l'.join(search_value1))]
fig=df2.plot(x='time',y=['X','Y','Z'],kind='line')
plt.pyplot.show()
fig1=fig.get_figure()
fig1.savefig('C:\\Users\\Mehedi\\PycharmProjects\\datasets\\lift_up\\acc10.png', dpi=1200)

df3=df1[df1.Sensor.str.contains('l'.join(search_value2))]
fig=df3.plot(x='time',y=['X','Y','Z'],kind='line')
plt.pyplot.show()
fig2=fig.get_figure()
fig2.savefig('C:\\Users\\Mehedi\\PycharmProjects\\datasets\\lift_up\\gyro10.png', dpi=1200)
