#include "Validator.h"

int validateAddition(VectorList * vector, TElem newElement)
{
	int i;
	for (i = 0; i < getNumberOfElements(vector); i++) {
		TElem *element = (TElem *) malloc(sizeof(TElem));
		getValueByIndex(vector, i, element);
		if (strcmp(newElement.name, element->name) == 0)
			if (newElement.concentration == element->concentration) {
				free(element->name);
				free(element);
				return -1;
			}
		free(element->name);
			free(element);
	}
	return 0;
}
