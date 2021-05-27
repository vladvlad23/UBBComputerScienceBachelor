//
// Created by Vlad on 29-May-19.
//

#include "Matrix.h"

Matrix::Matrix(int nrLines, int nrCols) : lines(nrLines), columns(nrCols)
{
}

int Matrix::nrLines() const
{
    return lines;
}

int Matrix::nrColumns() const
{
    return columns;
}

TElem Matrix::element(int i, int j) const
{
    if(i<0 || j<0 || i>=lines || j>=columns)
        throw std::invalid_argument("Invalid line/column\n");
    MatrixNode node;
    node.line = i;
    node.column = j;
    if(tree.findNode(node)==nullptr)
    {
        return NULL_TELEM;
    }
    return tree.findNode(node)->value.value;
}

TElem Matrix::modify(int i, int j, TElem e)
{
    if(i<0 || j<0 || i>=lines || j>=columns)
        throw std::invalid_argument("Invalid line/column\n");
    MatrixNode node;
    node.value = e;
    node.line = i;
    node.column = j;
    TElem returnElement;
    if(tree.findNode(node)!=NULL)
    {
        returnElement = tree.findNode(node)->value.value;
        if(e!=NULL_TELEM)
        {
            tree.changeValue(node);
        }
        else
        {
            tree.deleteValue(node);
        }
    }
    else // the node isn't there
    {
        returnElement = NULL_TELEM;
        if(e!=NULL_TELEM)
            tree.insertNode(node);

    }
    return returnElement;
}
