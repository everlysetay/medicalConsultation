#Add script to:
# * Install dependencies
# * Build/Compile
# * Run Test Suit to validate
#
# After this is run, jar file
# this should work

#Install Dependencies
#/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
#brew install gradle

#Build/Compile
gradle assemble
gradle build

#Run Junit Tests
gradle test
