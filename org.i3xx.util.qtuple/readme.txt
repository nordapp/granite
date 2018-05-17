The artifact is inspired by the OSGi service registry and is written to find resources by key-value-pairs.
See 'query.txt' to get a description of the search language.

The usage is just simple:

//The services you test can give their data as a 'Map<String, String>' map to you. It is always
//a good thing to use JSON for this purpose. Example configuration with JSON: '{"myOwnKey":"simple"}'
//JSON can be parsed to map in the right format. Follow the manual of your JSON parser.
Map<String, String> map = ...;

//First create a Resolver that contains your query
//we suggest you need the service with the key named 'myOwnKey' and the value 'simple'
Resolver r = new Resolver("myOwnKey=simple");

//Then resolve against a map of key-value-pairs
boolean f = r.resolve(map)

//If the query matches the values in the map, the resolver returns 'true', otherwise 'false'.

//Hint: To test a key use a regular expression that matches anything. "myOwnKey~.*"



