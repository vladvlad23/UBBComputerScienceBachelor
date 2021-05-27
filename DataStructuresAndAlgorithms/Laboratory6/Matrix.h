#pragma once



typedef int TElem;

#define NULL_TELEM 0


class MatrixIterator;

class Matrix {

    friend class MatrixIterator;

    struct Node {
        int line,column;
        TElem value;
        Node *next;
    };


private:

    /*representation of the matrix*/
    Node **elements;
    int loadFactor,lineNumber,columnNumber,size,capacity;
    bool rehash = false;
    const int maxLoadFactor = 0.5;
    int hashFunction(int line,int column) const;
    void doubleArraySize();
    void rehashElements();
    void freeList(Node *list);
    int computeLoadFactor() const;
    Matrix::Node *getNodeFromList(Node *head, int line, int column) const;




public:


    //constructor

    //throws exception if nrLines or nrCols is negative or zero

    Matrix(int nrLines, int nrCols);

    ~Matrix();

    void printMatrix() const;



    //returns the number of lines

    int nrLines() const;



    //returns the number of columns

    int nrColumns() const;



    //returns the element from line i and column j (indexing starts from 0)

    //throws exception if (i,j) is not a valid position in the Matrix

    TElem element(int i, int j) const;




    //modifies the value from line i and column j

    //returns the previous value from the position

    //throws exception if (i,j) is not a valid position in the Matrix

    TElem modify(int i, int j, TElem e);

    MatrixIterator iterator(int column);

};
