abundance_data <- read.csv('seatube_abundance_data.csv')
attribute_data <- read.csv('seatube_attribute_data.csv')
rownames(abundance_data) <- abundance_data[,1]
abundance_data <- abundance_data[,-1]
rownames(attribute_data) <- attribute_data[,1]
attribute_data <- attribute_data[,-1]
abundance_data2 = abundance_data[match(rownames(abundance_data), rownames(attribute_data)),]
seatube_data = cbind(attribute_data, abundance_data2)
ShannonDiv_function <- function(x) {- sum(x * log(x), na.rm=T)}
GiniSimpsonDiv_function <- function(x) {1-sum((x^2),na.rm=T)}
richness_function<-function(x){sum(x)}
fine_data <- subset(seatube_data, bottom_type == "fine")
Clayoquot_data <- subset(fine_data, location == "clayoquot_slope")
#test <- apply(Clayoquot_data[,-c(1:8)]!=0, 1, FUN = richness_function)
#test
With_Biotic<-subset(Clayoquot_data,sea_pen!=0|coral!=0) #check the one with biotic
rich1<- apply(With_Biotic[,-c(1:8)]!=0, 1, FUN = richness_function)
one_prop <- apply(With_Biotic[, -c(1:8)], 1, proportions)
one_prop[one_prop == 0] <- NA
shan1 <- apply(one_prop, 2, FUN = ShannonDiv_function)
gini1<-apply(one_prop,2,FUN=GiniSimpsonDiv_function)
mean(rich1)
mean(shan1)
mean(gini1)
With_Biotic2<-subset(Clayoquot_data,coral==0 & sea_pen==0) #check the one without biotic  
rich2<- apply(With_Biotic2[,-c(1:8)]!=0, 1, FUN = richness_function)
two_prop <- apply(With_Biotic2[, -c(1:8)], 1, proportions)
two_prop[two_prop == 0] <- NA
shan2 <- apply(two_prop, 2, FUN = ShannonDiv_function)
gini2<-apply(two_prop,2,FUN=GiniSimpsonDiv_function)
mean(rich2)
mean(shan2)
mean(gini2)
boxplot(rich1, main = "Substrates with Biotic Habitats")
boxplot(rich2, main = "Substrates without Biotic Habitats")
#trend
With_Biotic$sum2 = rowSums(With_Biotic[,c("coral", "sea_pen")])
With_Biotic=With_Biotic[order(With_Biotic$sum2),]
three_prop <- apply(With_Biotic[, -c(1:8)], 1, proportions)
three_prop[three_prop == 0] <- NA
rich3<- apply(With_Biotic[,-c(1:8)]!=0, 1, FUN = richness_function)
shan3 <- apply(three_prop, 2, FUN = ShannonDiv_function)
gini3<-apply(three_prop,2,FUN=GiniSimpsonDiv_function)
shan_plot=data.frame( With_Biotic["sum2"],shan3)
gini_plot=data.frame( With_Biotic["sum2"],gini3)
rich_plot=data.frame( With_Biotic["sum2"],rich3)
plot(shan_plot$sum2,shan_plot$shan3,type="l",main="Shannon's Diversity with increasing biotic habitat",xlab = "Sum of biotic habitats(individuals)",ylab = "Shannon's Diversity")
plot(gini_plot$sum2,gini_plot$gini3,type="l",main="Gini-Simpson with increasing biotic habitat",xlab = "Sum of biotic habitats(individuals)",ylab = "Gini-Simpson")
plot(rich_plot$sum2,rich_plot$rich3,type="l",main="Species richness with increasing biotic habitat",xlab = "Sum of biotic habitats(individuals)",ylab = "Species Richness")
#complexity
with_coral=subset(Clayoquot_data,sea_pen==0&coral!=0)
rich4<- apply(with_coral[,-c(1:8)]!=0, 1, FUN = richness_function)
four_prop <- apply(with_coral[, -c(1:8)], 1, proportions)
four_prop[four_prop == 0] <- NA
shan4 <- apply(four_prop, 2, FUN = ShannonDiv_function)
gini4<-apply(four_prop,2,FUN=GiniSimpsonDiv_function)
mean(rich4)
mean(shan4)
mean(gini4)
with_pen=subset(Clayoquot_data,sea_pen!=0&coral==0)
rich5<- apply(with_pen[,-c(1:8)]!=0, 1, FUN = richness_function)
five_prop <- apply(with_pen[, -c(1:8)], 1, proportions)
five_prop[five_prop == 0] <- NA
shan5 <- apply(five_prop, 2, FUN = ShannonDiv_function)
gini5<-apply(five_prop,2,FUN=GiniSimpsonDiv_function)
mean(rich5)
mean(shan5)
mean(gini5)
boxplot(shan4, main = "Gini-Simpson of Substrates with Coral")
boxplot(shan5, main = "Gini-Simpson of Substrates with Sea_pen")
