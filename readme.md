# SSIT (Super Simple Issue Tracker)
## Synopsis
A simple, feature light, command line issue tracker, that saves all issues as text files, suitable for tracking using a source code configuration management tool.

## Usage Example
To initialise a new SSIT project enter the following command in the root directory of your project.
```
> ssit init
```

To record a new issue enter this command.
```
> ssit todo "Add a splash screen to my application."
```

To see open issues enter this command:
```
> ssit list
```

To record that an issue is resolved use its ID number in this command.
```
> ssit close 3
```

That's it.

## Motivation
SSIT is intended to fulfill the issue tracking needs of very small (possibly one man projects).

It uses plain text files to record issue data so that changes to issues can be easily tracked in source code configuration management tools like SVN or GIT.

## Installation
To start using the tool...

* Build the tool using Gradle.
```
> gradlew build
```
* Update your path to include the root of the SSIT project.
* Run the *ssit* command in another directory to confirm the installation has worked and to see the tool's command line usage.

## Tests
You can run all the SSIT unit tests with the Gradle command...
```
> gradlew test
```

## Development
In the spirit of "eating your own dog food" the SSIT development issues are tracked in SSIT.
That means, if you've got the code, you've also got the issues.

If you want to make a contribution, please include the update to the SSIT issue you've fixed in the pull request.