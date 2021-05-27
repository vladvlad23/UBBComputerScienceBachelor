//
// Created by Vlad on 20-May-19.
//

#ifndef LABORATORY6_MATRIXITERATOR_H
#define LABORATORY6_MATRIXITERATOR_H


#include "Matrix.h"

class MatrixIterator
{

private:
    int line,column;
    const Matrix &container;
public:

    MatrixIterator(const Matrix &container,int column) : container(container),column(column) {line=0;}

    void first();
    void next();
    bool valid();
    TElem getElement();

};


#endif //LABORATORY6_MATRIXITERATOR_H
