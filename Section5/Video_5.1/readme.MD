### SpringSecurity-MongoDBUserDetailsService

  * Download and Install MongoDB
      https://www.mongodb.com/download-center
  
  * Open CMD and run MongoDB service - mongod.exe placed under /bin
    * If Failed due to non existing data/db , create directory manually
    * Run mongod.exe again
    
  * Keep the CMD with mongod.exe service, open to keep mongoDB service alive.
  
  * Open another terminal/cmd, run mongo.exe placed under /bin
  * After prompt , run - use local
  * Clean and build project in eclipse
  * Run "InsertIntoMongo" as Java Application, placed under src/test/java , to insert users into MongoDB
  * Run on db prompt - db.userTo.find() - to fetch users.
  * Run Project on Server and log in to application.
