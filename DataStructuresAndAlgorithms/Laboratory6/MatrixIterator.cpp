//
// Created by Vlad on 20-May-19.
//

#include <stdexcept>
#include "MatrixIterator.h"
#include "Matrix.h"

void MatrixIterator::first()
{
    line = 0;
}

void MatrixIterator::next()
{
    if(!valid())
    {
        throw std::invalid_argument("Not valid iterator\n");
    }
    line++;
}

bool MatrixIterator::valid()
{
    return line < container.lineNumber;
}

TElem MatrixIterator::getElement()
{
    return container.element(line,column);
}

/*
def subalgorithm name(matrix,column)
    precond: matrix is matrix
    post: returns an element of type TElem
    descr: returns an element of type TElem

    name <- matrix.element(line,column)
end subalgorithm
*/