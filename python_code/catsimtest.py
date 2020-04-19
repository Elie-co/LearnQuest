#Author : Elie

import numpy as np
import math

theta=np.random.randint(low=-4, high=5, size=(1,20))
delta=np.random.randint(low=-4, high=5, size=(1,20))
luck=np.random.uniform(low=0, high=0.25, size=(1,20))
theta=1
delta=1
luck=0.25

print(theta,"Learner level")
print(delta,"Exercise difficulty")
print(luck,"Luck\n")

# If theta is high, learner is good
# -4 < theta < 4  &  -4 < b < 4
# If b is high, the exercise is difficult



def probsuccess(theta, delta, luck):
    discriminant = 1.7
    print("theta: ",theta)
    print("delta: ", delta)
    print("proba: ", luck + (1 - luck)/(1 + math.exp(-discriminant*(theta - delta))))
    return luck + (1 - luck)/(1 + math.exp(-discriminant*(theta - delta)))
print(probsuccess(theta,delta,luck))

def computetheta():
    theta = 1
    theta *= delta - math.log((1-luck)/(0.625 - luck))
    return theta
print(computetheta())
probabilities=[]
"""
with np.nditer(proba, op_flags=['readwrite']) as it:
    for i in it:
        a=float(i)
        probabilities.append(a)
        if 0.66 <= a and a<= 0.75:
            print("Hello")
print(probabilities)
""" 
