# Preprocessing
uniqueRecords <- unique(KioskCoords)
features <- uniqueRecords[,3:4]
means <- apply(features, 2, mean)
sd <- apply(features, 2, sd)
featuresAfterStandardization <- scale(features, means, sd)

# kmeansClustering
kmeansClusters <- kmeans(featuresAfterStandardization, 2)

# Visualization
plot(df[c("latitude (N)", "longitude (N)")], col=kmeansClusters$cluster)

# Appending Cluster details to input data
install.packages("dplyr")
library("dplyr")
kioskWithClusterId <- mutate(uniqueRecords, clusterInstance = kmeansClusters$cluster)

#Write to csv file
write.csv(kioskWithClusterId, "kioskCoordsAfterClustering.csv")