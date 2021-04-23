# S3 Retrieve Binary
little example of how to retrieve a file from AWS S3 using its SDK and display it on the browser

# JDK

This project was made for JDK 16

# Preconditions
To the correct functionality of this project you must setup
the next environment variables

* AWS_ACCESS_KEY_ID
* AWS_SECRET_ACCESS_KEY

with you own values from the AWS account
also its necessary to create a bucket to store the files 
for this example a PDF file was uploaded in order to show it 
on the browser 

# Spring variables
In the project there are 2 variables on the spring properties

* aws.region: store the region where you are worcking 
* aws.bucket.name: store the name of the bucket where you pretend 
to retrieve the file
  
file free to change those as you require

# Endpoint
The only endpoint to test is

~~~~~
/get/pdf/{fileName}/{directory}
~~~~~

you can test it on the browser, the directory is not necessary
only if there is a folder inside the bucket