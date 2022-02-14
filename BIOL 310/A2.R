#Q1.
#spp richness in 1995 and 2009
species_1995 <- c("Saxifraga ferruginea", "Agrostis pallens", "Cistanthe umbellata",
                  "Anaphalis margaritacea", "Carex mertensii", "Carex microptera",
                  "Chamerion angustifolium", "Epilobium anagallidifolium",
                  "Juncus mertensianus", "Juncus parryi", "Luetkea pectinata",
                  "Poa secunda", "Polytrichum juniperinum", "Racomitreium canescens")
plant.cover_1995 <- c(0.5,0.4,0.2,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1)
#in 2009
species_2009 <- c("Penstemon cardwellii","Cryptogramma cascadensis","Agrostis pallens",
                  "Carex pachystachya","Juncus parryi","Agrostis scabra",
                  "Arctostaphylos nevadensis","Hieracium albiflorum","Luetkea pectinata",
                  "Luzula parviflora","Racomitreium canescens")
plant.cover_2009 <- c(3.1,1.4,0.2,0.2,0.2,0.1,0.1,0.1,0.1,0.1,0.1)
richness_1995=length(unique(species_1995))
richness_2009=length(unique(species_2009))
richness_1995
richness_2009
#Shannon' Diversity 
prop_1995=plant.cover_1995/sum(plant.cover_1995)
ShannonDiv_1995=-sum(prop_1995 * log(prop_1995))
ShannonDiv_1995
prop_2009=plant.cover_2009/sum(plant.cover_2009)
ShannonDiv_2009=-sum(prop_2009 * log(prop_2009))
ShannonDiv_2009
#Simpons and Gini
Simpon_1995=sum(prop_1995^2)
Gini_1995=1-Simpon_1995
Simpon_1995
Gini_1995
Simpon_2009=sum(prop_2009^2)
Gini_2009=1-Simpon_2009
Simpon_2009
Gini_2009
#Q2. 
abundance_data <- read.csv('seatube_abundance_data.csv')
attribute_data <- read.csv('seatube_attribute_data.csv')
rownames(abundance_data) <- abundance_data[,1]
abundance_data <- abundance_data[,-1]
rownames(attribute_data) <- attribute_data[,1]
attribute_data <- attribute_data[,-1]
View(abundance_data)
View(attribute_data)
abundance_data2 = abundance_data[match(rownames(abundance_data), rownames(attribute_data)),]
seatube_data = cbind(attribute_data, abundance_data2)
ShannonDiv_function <- function(x) {- sum(x * log(x), na.rm=T)}
fine_data <- subset(seatube_data, bottom_type == "fine")
boulder_data <- subset(seatube_data, bottom_type == "boulder")
#Species richness
#boulder
boulder_richness=rowSums(boulder_data[,-c(1:8)]!=0)
boulder_richness
#fine
fine_richness=rowSums(fine_data[,-c(1:8)]!=0)
mean(fine_richness)
mean(boulder_richness)
#Shannon Diversity
#Boulder
boulder_prop <- apply(boulder_data[, -c(1:8)], 1, proportions)
boulder_prop[boulder_prop == 0] <- NA
ShannonDiv_boulder <- apply(boulder_prop, 2, FUN = ShannonDiv_function)
boxplot(ShannonDiv_boulder, main = "Shannon's Diveristy of SeaTube Boulder Sites")
#Fine
fine_prop <- apply(fine_data[, -c(1:8)], 1, proportions)
fine_prop[fine_prop == 0] <- NA
ShannonDiv_fine <- apply(fine_prop, 2, FUN = ShannonDiv_function)
boxplot(ShannonDiv_fine, main = "Shannon's Diveristy of SeaTube Fine Sites")
mean(ShannonDiv_boulder)
mean(ShannonDiv_fine)

#Q3.
#Gini#
GiniSimpsonDiv_function <- function(x) {1-sum((x^2),na.rm=T)}
#Endeavour
Endeavour_data <- subset(seatube_data, location == "endeavour")
Endeavour_prop <- apply(Endeavour_data[, -c(1:8)], 1, proportions)
Endeavour_prop[Endeavour_prop == 0] <- NA
Gini_Endeavour=apply(Endeavour_prop,2,FUN=GiniSimpsonDiv_function)
mean(Gini_Endeavour)
hist(Gini_Endeavour,main = "Gini-Simpson's Diveristy of Endeavour " )
boxplot(Gini_Endeavour, main = "Gini-Simpson's Diveristy of Endeavour ")
#Clayoquot Slope
Clayoquot_data <- subset(seatube_data, location == "clayoquot_slope")
Clayoquot_prop <- apply(Clayoquot_data[, -c(1:8)], 1, proportions)
Clayoquot_prop[Clayoquot_prop == 0] <- NA
Gini_Clay=apply(Clayoquot_prop,2,FUN=GiniSimpsonDiv_function)
mean(Gini_Clay)
hist(Gini_Clay,  main = "Gini-Simpson's Diveristy of Clayoquot Slope")
boxplot(Gini_Clay, main = "GiniSimpson's Diveristy of Clayoquot Slope")

#Q4
fp_data=subset(seatube_data,location=="folger_passage")
fp_pres <- colSums(fp_data[,-c(1:8)])
bc_data=subset(seatube_data,location=="barkley_canyon")
bc_pres <- colSums(bc_data[,-c(1:8)])
#SADs
hist(log(sort(fp_pres)),main="Species Abundance Distribution in Folger Passage", ylab='No. of Species', xlab='Number of Individuals')
hist(log(sort(bc_pres)),main="Species Abundance Distribution in Barkley Canyon", ylab='No. of Species', xlab='Number of Individuals')
#RADs
barplot(log(sort(fp_pres, TRUE, na.last = T)),main="Rank Abundance Diagram in Folger Passage",ylab='Abundance',las = 2, cex.names = 0.45)
barplot(log(sort(bc_pres, TRUE)),main="Rank Abundance Diagram in Barkley Canyon",ylab='Abundance',las = 2, cex.names = 0.45)

