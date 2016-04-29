# Unistall cmake in c9
    $ sudo apt-get remove cmake
    $ sudo apt-get autoremove
# Install cmake 3.4
    $ wget http://www.cmake.org/files/v3.4/cmake-3.4.1.tar.gz 
    $ tar -xvzf cmake-3.4.1.tar.gz 
    $ cd cmake-3.4.1/ 
    $ ./configure 
    $ make 
    $ sudo make install
    $ update-alternatives --install /usr/bin/cmake cmake /usr/local/bin/cmake 1 --force

# Install client c++ 11

    $ mkdir client-taquin-cpp
    $ cd taquin
    $ wget https://sourceforge.net/projects/boost/files/boost/1.58.0/boost_1_58_0.tar.gz
    $ tar -xzvf boost_1_58_0.tar.gz
    $ rm boost_1_58_0.tar.gz
    $ cmake -DBOOST_INCLUDEDIR=./boost_1_58_0 -DBOOST_LIBRARYDIR=./boost_1_58_0/libs -DBOOST_VER:STRING=1.58 .
    $ make
    $ ./sio_console_demo


To get you started with C/C++, we have created some small hello world
applications and a Makefile.

Have a look at the files, and use the terminal to build them:

    $ make
    $ ./hello-c-world
    $ ./hello-cpp-world

We're sure you can take it from there. Go ahead and edit the code, 
or add some new files. It's all up to you! 

Happy coding!


## Support & Documentation
...

