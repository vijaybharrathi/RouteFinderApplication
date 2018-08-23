# Route Finder Application

#### Assumptions:
1. The drivers will be able to supply all the products and required quantity at each kiosk.
2. The data given in the csv file is accurate enough for algorithm development purposes.
3. Capacity of the truck is always greater than the demand of the kiosks delivered by the driver. Constraints like, whether the kiosks demand has been met or if truck has sufficient capacity are not considered. Provided additional time, the products required at each kiosk and truck capacity can be considered as features for clustering the kiosks and prioritized the route based on additional features and not just the distance.


#### Clustering:
1. I have used R to cluster the input data. I have attached [R script](/src/org/routefinder/kmeansclustering/clusteringRscript.R)  and the cluster [Plot](/src/org/routefinder/kmeansclustering/kioskClusteringPlot.png) developed.
2. I chose kmeans algorithm(Internal Library available in R) for clustering since the data volume is less and it is less expensive. Fixed number of clusters to be 2 since the system is designed to find 2 disjoined routes. Can be configured to n so that the clustering is extensible.
3. The input data is preprocessed before applying clustering, which removes duplicate records and non numerical data available.
3. The features after pre processing are latitude and longitude for clustering. To improve accuracy, I have scaled the features with mean and standard deviation.
4. The clusterId generated after clustering was appended to the input csv file and written to the [sources location] which will serve as input to the Route Finder Application.


#### Routing:
1. Considering application extensibility, the params required for the application to run are fetched from the [Configuration Class](/src/org/routefinder/configuration/ConfigurationParamters.java). 
2. After clustering, there were 2 clusters with 41 and 7 kiosks. Since the number of kiosks to delivered by drivers are less, I decided to do Greedy algorithm for routing. Finding the next possible optimal solution at each step.
3. The routing algorithm is as follows. 
    1. Generated distanceMatrix where distanceMatrix[i][j] = distance between kiosk i and kiosk j.
    2. Starting from Kitchen, the aim is to find the next nearest kiosk and compute the minimum distance for each visit.
    3. The loop stops when all the kiosks are visited.
4. The findShortestRoute() method in RoutingHelper class is called for each cluster. So the complexity would by n! for each cluster which in this case is 41! + 7! (2 Duplicate records in the input data)  

#### To Run:
1. Download the git repo
2. If required changed the params in [Configuration Class](/src/org/routefinder/configuration/ConfigurationParamters.java)
3. Run [Main class](/src/org/routefinder/Main.java)
4. Output shows the Route to be travelled by each driver and euclidean distance covered by the driver. 