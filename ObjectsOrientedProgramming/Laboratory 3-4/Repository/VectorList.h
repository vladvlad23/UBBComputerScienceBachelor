#pragma once
#include <stdlib.h>
#include "../Domain/Medicine.h"
#include <string.h>
#include <stdlib.h>

typedef Medication TElem;

typedef struct {
	int size;
	int numberOfElements;
	void *elements;
} VectorList;

typedef struct {
    int size;
    int numberOfOperations;
    int currentIndex;
    VectorList *operations;
}Operations;

typedef struct {
    int command,relevantIndex;
    TElem *oldElement;
}TypeOfOperation;

typedef struct {
    int size;
    int numberOfOperations;
    int currentIndex;
    TypeOfOperation *operations;
}OperationsSecondEdition;

int createOperationsSecondEdition(OperationsSecondEdition *);
int doubleOperationSizeSecondEdition(OperationsSecondEdition *);
int addOperationToListSecondEdition(OperationsSecondEdition *,TElem,int,int);
int destroyOperationsSecondEdition(OperationsSecondEdition *);
int undoOperationSecondEdition(OperationsSecondEdition *,VectorList *);
int redoOperationSecondEdition(OperationsSecondEdition *,VectorList *);


int createOperations(Operations **,VectorList *);
int doubleOperationSize(Operations *);
int addOperationToList(Operations *,VectorList *);
int destroyOperations(Operations **);
int undoOperation(Operations *,VectorList *);
int redoOperation(Operations *,VectorList *);



int VectorCreate(VectorList **list);
int VectorDestroy(VectorList **list);
int VectorDestroyElementNames(VectorList *Vector);
int VectorDestroyElements(VectorList *Vector);

int addToEnd(VectorList *Vector, TElem Value);
int removeByIndex(VectorList *Vector, int Index);
int getValueByIndex(VectorList *Vector, int Index, TElem*);
int getNumberOfElements(VectorList *Vector);
int replaceElement(VectorList *Vector, int Index, TElem newValue);
int increaseQuantity(VectorList *Vector, TElem *Value, int quantity);
void addInitialElements(VectorList *);

//void sort(VectorList *Vector);