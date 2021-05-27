#pragma once
#include "../Repository/VectorList.h"

int addToEndController(VectorList *Vector, TElem Value);
int removeByIndexController(VectorList *Vector, int Index);
int getValueByIndexController(VectorList *Vector, int Index, TElem*);
int getNumberOfElementsController(VectorList *Vector);
int modifyElementController(VectorList *, int, TElem );
int deleteMedicationController(VectorList *, char *,int);
int sortElementAscendingly(TElem *results, int numberOfElements);
int sortElementDescendingly(TElem *results, int numberOfElements);
int modifyConcentrationController(VectorList*,char *,int,int);
int modifyNameController(VectorList*,char *,int, char *);
int modifyQuantityController(VectorList*,char *,int,int);
int modifyPriceController(VectorList*,char *, int, int );
