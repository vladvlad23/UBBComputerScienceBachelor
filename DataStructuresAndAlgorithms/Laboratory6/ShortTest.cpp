#include <assert.h>
#include <bits/exception.h>
#include "Matrix.h"
#include "ShortTest.h"
#include "MatrixIterator.h"


using namespace std;

void testAll() { 
	Matrix m(4, 4);
	assert(m.nrLines() == 4);
	assert(m.nrColumns() == 4);	
	m.modify(1, 1, 5);
	assert(m.element(1, 1) == 5);
	m.modify(1, 1, 6);
	assert(m.element(1, 2) == 0);
}

void personalTest()
{
    Matrix m(3, 3);
    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            assert(m.element(i, j) == 0);

    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            m.modify(i, j, i + j);


    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            assert(m.element(i, j) == i + j);
}

void testLabAssignment()
{

    /*
     * 0 1 2
     * 1 2 3
     * 2 3 4
     */
    Matrix m(3,3);
    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            m.modify(i, j, i + j);

    MatrixIterator it = m.iterator(0);
    MatrixIterator it2 = m.iterator(1);
    MatrixIterator it3 = m.iterator(2);

    try
    {
        MatrixIterator itFalse = m.iterator(-1);
        assert(false);
    }
    catch(std::exception &ex)
    {

    }

    assert(it.getElement()==0);
    it.next();
    assert(it.getElement()==1);
    it.next();
    assert(it.getElement()==2);
    it.next();

    assert(it.valid() == false);

    assert(it2.getElement()==1);
    it2.next();
    assert(it2.getElement()==2);
    it2.next();
    assert(it2.getElement()==3);
    it2.next();

    assert(!it2.valid());

    assert(it3.getElement()==2);
    it3.next();
    assert(it3.getElement()==3);
    it3.next();
    assert(it3.getElement()==4);
    it3.next();

    assert(!it2.valid());

}