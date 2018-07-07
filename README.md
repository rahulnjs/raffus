# raffus

![Imgur](https://i.imgur.com/fPILxU7.png)


RaffuS help document,

-p        port

          Port to be used to bind the address,
          if not present default port 4343
          e.g. raffus -p 8080 

-d        Document Base

          Defines the root directory of the server
          if not present then the currect directory
          will beused as root.
          e.g. raffus -p <port> -d <root dir>

--v       version

          Server version.

-help     help

          See help document.

-c        caching

          To specify the max age of a cached file,
          use -1 for no caching.
          e.g. -c <max-age>

-l        logging

          If present, requests are logged to the
          console.


-verb     verbose

          To log raw requests
