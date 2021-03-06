# MVAR PDF REST Utility [![Build Status](https://travis-ci.com/mrisney/mvar-pdf.svg?branch=master)](https://travis-ci.com/mrisney/mvar-pdf)



[![N|Jersey](https://jersey.github.io/images/jersey_logo.png)](https://jersey.github.io/) 
###### Powered by Jersey REST

MVAR PDF Utility is a simple Jersey REST solution that takes a crash id and merges a collection of Blobs in an Oracle Database into one PDF file and either merge's or produces a download file.

  - The REST URL for download http://localhost:8080/mvar-pdf/api/download/{crashid}
  - The REST URL for merge http://localhost:8080/mvar-pdf/api/merge/{crashid}

### Tech

MVAR PDF utility uses a number of open source projects to work properly:

* [Jersey REST] - https://jersey.github.io
* [PDFBox] - https://pdfbox.apache.org
* [Lombok] - https://projectlombok.org
