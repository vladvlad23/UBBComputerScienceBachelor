//
// Created by Vlad on 13-Mar-19.
//

#include "UserInterface.h"
void listMedications(VectorList *medicationsList)
{
    int i, j, ascending;
    setbuf(stdout, 0);
    printf("Please give the string. All medicines will be considered if the string is empty: ");
    char *search = (char *)malloc(100 * sizeof(char));
    TElem *results = (TElem *)malloc(100 * sizeof(TElem *));
    fgets(search, 100, stdin);
    fgets(search, 100, stdin);
    setbuf(stdout, 0);
    printf("Would you like to sort ascending or descending by name? 1 for asc, 0 for desc: ");
    scanf("%d", &ascending);
    if (strcmp(search,"\n") == 0)
    {
        for (i = 0; i < getNumberOfElementsController(medicationsList); i++) //add everything to results so as to not disturb initial order
            getValueByIndexController(medicationsList, i, &results[i]);

        if (ascending != 0)
        {
            if (sortElementAscendingly(results, getNumberOfElementsController(medicationsList)) != 0)
                printf("Error at sorting");
        }
        else
        {
            if (sortElementDescendingly(results, getNumberOfElementsController(medicationsList)) != 0)
                printf("Error at sorting");
        }

        //printing
        for (i = 0; i < getNumberOfElementsController(medicationsList); i++)
        {

            printf("Name: %s\n", results[i].name);
            printf("Quantity: %d\n", results[i].quantity);
            printf("Concentration: %d\n", results[i].concentration);
            printf("Price: %d\n", results[i].price);
            printf("\n\n");
            free(results[i].name);
        }
    }
    else
    {
        int numberOfResults = 0;
        for (i = 0; i < getNumberOfElementsController(medicationsList); i++)
        {

            TElem medicine;
            getValueByIndex(medicationsList, i, &medicine);
            if (strstr(medicine.name, search))
            {
                //a better thing would be to use insert sort and have them directly inserted, thus obtaining
                //a complexity of O(n^2), but since efficiency is not required, i will add them to the
                //list of string and sort them which will take O(n^3)
                results[numberOfResults] = medicine;
            }
        }
        //sorting
        for (i = 0; i < numberOfResults - 1; i++)
        {
            for (j = i + 1; j < numberOfResults; j++)
            {
                if (strcmp(results[i].name, results[j].name) == 1)
                {
                    TElem temp = results[i];
                    results[i] = results[j];
                    results[j] = temp;
                }
            }
        }
        //printing
        for (i = 0; i < numberOfResults; i++)
        {
            printf("Name: %s\n", results[i].name);
            printf("Quantity: %d\n", results[i].quantity);
            printf("Concentration: %d\n", results[i].concentration);
            printf("Price: %d\n", results[i].price);
            printf("\n\n");
            free(results[i].name);
        }
    }

    free(results);
    free(search);
}

void addMedication(VectorList *medicationsList)
{
    char *name = (char *)malloc(100 * sizeof(char));
    int concentration, quantity, price;
    setbuf(stdout, 0);
    printf("Please give the name: ");
    fgets(name, 100, stdin);
    fgets(name,100,stdin);

    if (name[strlen(name) - 1] == '\n')
        name[strlen(name) - 1] = 0;
    setbuf(stdout, 0);
    printf("\nPlease give the concentration: ");
    scanf("%d", &concentration);

    setbuf(stdout, 0);
    printf("\nPlease give the quantity: ");
    scanf("%d", &quantity);

    setbuf(stdout, 0);
    printf("\nPlease give the price: ");
    scanf("%d", &price);

    TElem medication;
    medication.name = name;
    medication.concentration = concentration;
    medication.quantity = quantity;
    medication.price = price;


    if (addToEndController(medicationsList, medication) != 0) {
        printf("Error when adding to controller with either invalid input or with duplicate\n. If duplicate, the quantity has "
               "been increased and the rest disregarded :) \n");
        //it was not added so if i don't free it now, it will never be free
        free(medication.name);
    }

}

void deleteMedication(VectorList *medicationsList)
{
    char *name = (char *)malloc(100*sizeof(char));
    int concentration;
    printf("Please give the name of the medication you want to delete: ");
    getchar();
    fgets(name, 100, stdin);
    if (name[strlen(name) - 1] == '\n')
        name[strlen(name) - 1] = 0;
    printf("\nPlease give the concentration of the medication you want to delete: ");
    scanf("%d", &concentration);
    if (deleteMedicationController(medicationsList,name,concentration) == 0) {
        printf("\nElement deleted successfully.");
    }
    else {
        printf("\nNo such element. Sorry.");
    }
    free(name);
}

void updateMedication(VectorList *medicationsList)
{
    char *name = (char *)malloc(100*sizeof(char));
    int concentration, modification;
    setbuf(stdout, 0);
    printf("Please give the name of the medication you want to modify: ");
    getchar();
    fgets(name, 100, stdin);
    if (name[strlen(name) - 1] == '\n')
        name[strlen(name) - 1] = 0;
    setbuf(stdout, 0);
    printf("\nPlease give the concentration of the medication you want to modify: ");
    scanf("%d", &concentration);

    setbuf(stdout, 0);
    printf("\nWhat do you want to modify? 1 for concentration, 2 for name, 3 for quantity, 4 for price");
    scanf("%d", &modification);


    int newParameter;
    if (modification == 1)
    {
        setbuf(stdout, 0);
        printf("Please give concentration");
        scanf("%d", &newParameter);
        int i;
        if(modifyConcentrationController(medicationsList,name,concentration,newParameter)!=0)
            printf("Error when modifying\n");
    }
    else if (modification ==2 )
    {
        char *newName = (char *)malloc(100*sizeof(char));
        setbuf(stdout, 0);
        printf("Please give name");
        fgets(newName, 100, stdin);
        fgets(newName, 100, stdin);
        if (newName[strlen(newName) - 1] == '\n')
            newName[strlen(newName) - 1] = 0;
        int i;
        if(modifyNameController(medicationsList,name,concentration,newName)!=0)
            printf("Error when modifying\n");
        free(newName);
    }
    else if (modification == 3 )
    {
        setbuf(stdout, 0);
        printf("Please give quantity");
        scanf("%d", &newParameter);
        modifyQuantityController(medicationsList,name,concentration,newParameter);
    }
    else if(modification == 4)
    {
        setbuf(stdout, 0);
        printf("Please give price");
        scanf("%d",&newParameter);
        modifyPriceController(medicationsList,name,concentration,newParameter);
    }
    free(name);
}

void shortSupply(VectorList *medicationsList)
{
    int upperBound;
    setbuf(stdout,0);
    printf("Please give the upper bound: ");
    scanf("%d",&upperBound);
    int i;
    for(i=0;i<getNumberOfElements(medicationsList);i++)
    {
        TElem temporaryElement;
        if(getValueByIndexController(medicationsList,i,&temporaryElement) !=0 )
        {
            setbuf(stdout,0);
            printf("Error when reading the list.\n");
            return;
        }
        else
        {
            if(temporaryElement.quantity<upperBound)
            {
                printf("Name: %s\n", temporaryElement.name);
                printf("Quantity: %d\n", temporaryElement.quantity);
                printf("Concentration: %d\n", temporaryElement.concentration);
                printf("Price: %d\n", temporaryElement.price);
                printf("\n\n");
            }
        }
        free(temporaryElement.name);
    }
}

void overPriced(VectorList *medicationsList)
{
    int lowerBound;
    setbuf(stdout,0);
    printf("Please give the upper bound: ");
    scanf("%d",&lowerBound);
    int i;
    for(i=0;i<getNumberOfElements(medicationsList);i++)
    {
        TElem temporaryElement;
        if(getValueByIndexController(medicationsList,i,&temporaryElement) !=0 )
        {
            setbuf(stdout,0);
            printf("Error when reading the list.\n");
            return;
        }
        else
        {
            if(temporaryElement.price>lowerBound)
            {
                printf("Name: %s\n", temporaryElement.name);
                printf("Quantity: %d\n", temporaryElement.quantity);
                printf("Concentration: %d\n", temporaryElement.concentration);
                printf("Price: %d\n", temporaryElement.price);
                printf("\n\n");
            }
        }
        free(temporaryElement.name);
    }
}

void printHelp()
{
    setbuf(stdout, 0);
    printf("Welcome to Smiles Pharmacy (which most assume sells lots of antidepressants) Here are the commands : \n\
0. Exit \n\
1. List all medications\n\
2. Add a medication\n\
3. Delete a medication\n\
4. Update a medication\n\
5. Short supply\n\
6. Overpriced (first bonus)\n\
7.Undo operation\n\
8.Redo operation\n");
}

void startConsole(Operations *operations,VectorList *medicationsList)
{
    int command;
    while (1)
    {
        setbuf(stdout, 0);
        printf("Please give command: ");
        scanf("%d", &command);

        switch (command)
        {
            case 0:
                return;
                break;
            case 1:
                listMedications(medicationsList);
                break;
            case 2:
                addMedication(medicationsList);
                addOperationToList(operations,medicationsList);
                break;
            case 3:
                deleteMedication(medicationsList);
                addOperationToList(operations,medicationsList);
                break;
            case 4:
                updateMedication(medicationsList);
                addOperationToList(operations,medicationsList);
                break;
            case 5:
                shortSupply(medicationsList);
                break;
            case 6:
                overPriced(medicationsList);
                break;
            case 7:
               undoOperation(operations,medicationsList);
               break;
            case 8:
                printf("Yay");
                redoOperation(operations,medicationsList);
                break;
            default:
                setbuf(stdout, 0);
                printf("No valid command has been issued. Try again. \n");
        }


    }
}