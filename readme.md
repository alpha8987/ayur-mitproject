Setup mongo db
--------------

start the service-
	START mongod.exe --port 27017 --dbpath "C:\mongodb\data"
	
enable auth : https://docs.mongodb.com/manual/tutorial/enable-authentication/

1) run mongo server 
	mongod.exe --port 27017 --dbpath "C:\mongodb\data"
2) connect to server 
	mongo --port 27017
3) create admin user 
	use admin
	db.createUser(
	  {
		user: "root",
		pwd: "p@ssword99",
		roles: [ { role: "userAdminAnyDatabase", db: "admin" }, "readWriteAnyDatabase" ]
	  }
	)

4) restart the mongo service with auth
	START mongod --auth --port 27017 --dbpath "C:\mongodb\data\db"  --bind_ip 127.0.0.1

5) authenticate during the connection
	mongo --port 27017 -u "root" -p "p@ssword99" --authenticationDatabase "admin"
	
6) authenticate after connecting
	mongo --port 27017
	use admin
	db.auth("root", "p@ssword99" )
	
7) create other users
	use ayurveda
	db.createUser(
	  {
		user: "ayur-db-user",
		pwd: "test123",
            roles: [ { role: "readWrite", db: "ayurveda" }]
	  }
	)
	
8) test login
    mongo --port 27017 -u "ayur-db-user" -p "test123" --authenticationDatabase "ayurveda"
