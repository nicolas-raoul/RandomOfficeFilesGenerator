RandomOfficeFilesGenerator
==========================

This tool generates files that are representative of what files could be found in a typical Japanese company.
The goal is especially to produce files with varied content.

It can be useful for test-loading. If you do test-loading with a million files that have the same content, then compression will make the results non-representative.

It can also be used to load-test indexing solutions (for instance Lucene/Solr), especially in ECM (Enterprise Content Management) servers.

Example usage, to generate one million 10KB files:

```
java -jar RandomOfficeFilesGenerator_0.1.jar 1000000 10000
```
