# Utility_Library_Java_Edition

## Table of Contents
* [Command7z](#Command7z)
* [Loggers](#Loggers)
  * [ErrorLogger](#ErrorLogger)
  * [TextLogger](#TextLogger)
  * [MailLogger](#MailLogger)
* [FileManipulator](#FileManipulator)
* [FileReader](#FileReader)

## Summary

This a library that includes many utilities that can be reused in any project. Its has a multple log classes, classes for file manipulation like combining files and moving them to other directories.

## Command7z

This class has to main functions, the first which allows you to uncompress a compressed files with extensions like .7z and .zip. It has four parameters that have to passed to the function the first being the file that is going uncompressed, the second being where the file is going to be saved to, the third is the password that is going to be used to uncompress the file. If no password is requiered pass in a empty string and the last parameter is the new name of the uncompressed file.
The Java Process Builder is used to execute the 7z command. Also keep in mind that 7z has to be installed on the computer/server where you are going to execute this code and finally you have to include 7z in your enviorment variable path.

## Loggers

There are 3 types of loggers in this library. Being the following loggers.

### ErrorLogger

The error logger is used log all the errors that can occur in all the exceptions in your code.

### TextLogger

The text logger is used to log everything that you want to track, this is basic tracer that can help keep track of where your execution is at an help you identify were your code stopped.
### MailLogger

The mail logger is used catch all the mail exceptions that can occur.

## FileManipulator

This class allows us to manipulate regular .txt files to rename them, make copies of the files or copies without the header, merge, move, delete files, etc.

## FileReader

This class has a function that reads a file and returns a list of strings

## DataBaseHandler

This class is used to open a connection to a database.
