# symmetric-group-utils
functions helping explore problems related to symmetric group Sn  (https://en.wikipedia.org/wiki/Symmetric_group)


<br/>
<br/>

### how to use this library?

- reference io.github.sajonaro/symmetric-group-utils library via deps.edn
- reference needed namespaces from the library in your client source code
<br/>

example:


- in deps.edn:
```
{:deps
 {io.github.sajonaro/symmetric-group-utils {:git/tag "v0.0.1" :git/sha "cb6762a"}}}
``` 

<br/>
<br/>

### to build a cli service locally 
- run:
```
sudo chmod +x build-cli_local.sh
./build-cli_local.sh

```
- then one can run grutils-cli application like so:
```
./grutils-cli -h

```
<br/>

### to build a cli service as docker container 
- run: sudo docker_build.sh
- then: sudo docker_run.sh

<br/>
