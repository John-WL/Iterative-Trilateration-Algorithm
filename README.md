# Iterative-Trilateration-Algorithm
Extremely simple algorithm that computes trilateration iteratively

This algorithm approximates the actual position of a body B into 3D space using "anchor points" A. 

It exploits the convergant behavior of successive approximations of the position of the body. 

The As are analogous to WiFi signal source positions, or satelite positions, for example. 

For this algorithm to work, you need to know precisely where your As will be at any given time. 

It works better if the estimated position is inside the shape formed by your As. 

It's also going to behave better if your point in 3D space isn't moving too fast. If it needs to move fast, then you can always increase the precision by playing with the settings. The more precise this algorithm computes, the worse it gets computationnaly. 

This algorithm computes in O(p), p being the precision of the approximation. 



