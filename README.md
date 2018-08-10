# MVAR PDF REST Utility


[![N|Solid](https://jersey.github.io/images/jersey_logo.png)](https://nodesource.com/products/nsolid) 
###### Powered by Jersey REST
#
#
MVAR PDF Utility is a simple Jersey REST solution that takes a crash id and merges a collection of Blobs in an Oracle Database into one PDF file and either merge's or produces a download file.

  - The REST URL for download http://localhost:8080/mvar-pdf/api/download/{crashid}
  - The REST URL for merge http://localhost:8080/mvar-pdf/api/merge/{crashid}

### Tech

Dillinger uses a number of open source projects to work properly:

* [Jersey REST] - https://jersey.github.io/
