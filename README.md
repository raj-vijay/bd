<!--
*** README Template
***
-->

<!--
*** Markdown "reference style" links are used for readability.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

<!-- BANNER / LOGO -->
<br />
<p align="center">
  <a href="https://github.com/raj-vijay/bd">
    <img src="images/databricks.svg" alt="Logo" width="334" height="81">
  </a>

  <h3 align="center">Databricks</h3>

  <p align="center">
    Repository for Big Data Processing
    <br />
    <a href="https://www.databricks.com/"><strong>Explore Databricks »</strong></a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
## Table of Contents

* [Big Data Processing](#BigData)
* [Repositories](#Repositories)
  * [Prerequisites](#prerequisites)
* [References](#References)



<!-- ABOUT -->
## Big Data

Data is now being generated at an unprecedented rate. The volume, velocity and variety of the data that is being produced means that traditional database architectures are no longer suitable to store, manage and analyse such data. As a result, organisations are now using distributed systems where parts of the data are stored in distributed databases and managed and analysed by distributed algorithms. 

Big data processing uses distributed architectures, frameworks and algorithms to store, manage and analyse large-scale datasets. This covers scenarios involving both static data and data in motion performing real-time data analytics.

Apache Spark has its architectural foundation in the resilient distributed dataset (RDD), a read-only multiset of data items distributed over a cluster of machines, that is maintained in a fault-tolerant way. The Dataframe API was released as an abstraction on top of the RDD, followed by the Dataset API. In Spark 1.x, the RDD was the primary application programming interface (API), but as of Spark 2.x use of the Dataset API is encouraged even though the RDD API is not deprecated. The RDD technology still underlies the Dataset API.

<!-- REPO -->
### Repositories

The repository for Big Data Processing is like a folder or storage space that contains all the files such as code, documentation, images, and is more related to my personal endeavours on the Big Data Processing problems using Apache Spark and Databricks Unified Data Analytics Platform.

<!-- PREREQUISITES -->
### Prerequisites

For datasets involving Kaggle, the following approach is used for integration.

Steps for Integration
1. Installing Kaggle Package for Google Colab Integration


```sh
# Install kaggle package for data integration
!pip install kaggle
```

2. Intergating security and authentication using the kaggle.json API token for authentication
```sh
# Create folder on the Google Colab file system
!mkdir ~/.kaggle
!cp /content/kaggle.json ~/.kaggle/kaggle.json

# Protect Kaggle JSON file for security reasons
!chmod 600 /root/.kaggle/kaggle.json
```

<!-- REFERENCES -->
## References

* [Databricks Unified Data Analytics Platform]( https://databricks.com/product/unified-data-analytics-platform)
* [Apache Spark]( http://spark.apache.org/)
* [Google Colaboratory]( https://colab.research.google.com/notebooks/intro.ipynb)
* [Spark Wiki](https://en.wikipedia.org/wiki/Apache_Spark)
* []()
