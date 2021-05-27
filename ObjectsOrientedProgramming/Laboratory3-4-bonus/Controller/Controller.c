#include "Controller.h"
#include "../Domain/Validator.h"

int addToEndController(VectorList *Vector, TElem Value)
{
    //Function will validate the addition of an element in the vectorlist and then will call the repo addition function
	if(validateAddition(Vector,Value) == 0) {
		if (addToEnd(Vector, Value) != 0)
			return -1;
	}
	else
		return -1;
	return 0;
}
int removeByIndexController(VectorList *Vector, int Index)
{
    //Function will call the repo removing of a certain element from the list and will return -1 if the index is invalid
	if (Vector == NULL)
		return -1;
	if (Index > Vector->numberOfElements || Index < 0) // not possible to remove something which does not exist
		return -1;

	if(removeByIndex(Vector, Index)==0)
		return 0;
	return -1;
}
int getValueByIndexController(VectorList *Vector, int Index, TElem* element)
{
    //Function will call the repo to get a certain element from the list and will return -1 if the index is invalid
	if (Vector == NULL)
		return -1;
	if (Index > Vector->numberOfElements || Index < 0)
		return -1;

	if (getValueByIndex(Vector, Index, element) == 0)
		return 0;
	return -1;

}
int getNumberOfElementsController(VectorList *Vector)
{
    //function will return the number of elements in the vectorlist
	return getNumberOfElements(Vector);
}

int modifyElementController(VectorList *Vector, int index, TElem newElement)
{
    //function will modify an element from the vectorlist after validating the input
	if (Vector == NULL)
		return -1;
	if (index > Vector->numberOfElements || index < 0)
		return -1;

	if (replaceElement(Vector, index, newElement) == 0)
		return 0;
	return -1;
}

int deleteMedicationController(VectorList *medicationList, char *name, int concentration)
{
	int i;
	for (i = 0; i < getNumberOfElementsController(medicationList); i++)
	{
		TElem element;
		getValueByIndexController(medicationList, i, &element);
		if (strcmp(element.name, name) == 0 && element.concentration == concentration)
		{
			removeByIndexController(medicationList, i);
			free(element.name);
			return 0;
		}
		free(element.name);
	}
	return -1;
}

int sortElementAscendingly(TElem *results,int numberOfElements)
{
	int i, j;
	for (i = 0; i < numberOfElements-1; i++)
		for (j = i + 1; j < numberOfElements; j++)
		{
			if (strcmp(results[i].name, results[j].name) > 0)
			{
				TElem temp = results[i];
				results[i] = results[j];
				results[j] = temp;
			}
		}
	return 0;
}

int sortElementDescendingly(TElem *results, int numberOfElements)
{
	int i, j;
	for (i = 0; i < numberOfElements - 1; i++)
		for (j = i + 1; j < numberOfElements; j++)
		{
			if (strcmp(results[i].name, results[j].name) < 0 )
			{
				TElem temp = results[i];
				results[i] = results[j];
				results[j] = temp;
			}
		}
	return 0;
}

int modifyConcentrationController(VectorList* medicationsList,char *name,int oldConcentration, int newConcentration)
{
    int i;
    TElem temporaryElement;
    for (i = 0; i < getNumberOfElements(medicationsList); i++)
    {
        if (getValueByIndexController(medicationsList, i, &temporaryElement) != 0) {
        	return -1;
		}
        if (temporaryElement.concentration == oldConcentration && strcmp(temporaryElement.name, name)==0)
        {
            free(((TElem*)medicationsList->elements)[i].name);
            temporaryElement.concentration = newConcentration;
			replaceElement(medicationsList, i, temporaryElement);
            return 0;
        }
        else{
            free(temporaryElement.name);
        }
    }
    return 0;
}
int modifyNameController(VectorList* medicationsList, char *oldName, int concentration, char *newName)
{

    int i;
    TElem temporaryElement;
    for (i = 0; i < getNumberOfElements(medicationsList); i++)
    {
        if (getValueByIndexController(medicationsList, i, &temporaryElement) != 0) {
			return -1;
		}
        if (temporaryElement.concentration == concentration && strcmp(temporaryElement.name, oldName)==0)
        {
            free(((TElem*)medicationsList->elements)[i].name);
            strcpy(temporaryElement.name,newName);
            replaceElement(medicationsList, i, temporaryElement);
            return 0;
        }
        else
        {
            free(temporaryElement.name);
        }
    }

    return 0;
}
int modifyQuantityController(VectorList* medicationsList,char *name, int concentration, int newQuantity)
{
    int i;
    TElem temporaryElement;
    for (i = 0; i < getNumberOfElements(medicationsList); i++)
    {
        if (getValueByIndexController(medicationsList, i, &temporaryElement) != 0) {
			return -1;
		}
        if (temporaryElement.concentration == concentration && strcmp(temporaryElement.name, name)==0)
        {
            free(((TElem*)medicationsList->elements)[i].name);
            temporaryElement.quantity = newQuantity;
            replaceElement(medicationsList, i, temporaryElement);
            return 0;
        }
        else
        {
            free(temporaryElement.name);
        }
    }
    return 0;
}
int modifyPriceController(VectorList* medicationsList,char *name,int concentration, int newPrice)
{
    int i;
    TElem temporaryElement;
    for (i = 0; i < getNumberOfElements(medicationsList); i++)
    {
        if (getValueByIndexController(medicationsList, i, &temporaryElement) != 0) {
        	return -1;
		}
        if (temporaryElement.concentration == concentration && strcmp(temporaryElement.name, name)==0)
        {
            free(((TElem*)medicationsList->elements)[i].name);
            temporaryElement.price = newPrice;
            replaceElement(medicationsList, i, temporaryElement);
            return 0;
        }
		else
		{
			free(temporaryElement.name);
		}
    }
    return 0;
}

