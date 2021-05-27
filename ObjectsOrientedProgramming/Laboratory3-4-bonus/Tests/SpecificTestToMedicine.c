//
// Created by Vlad on 17-Mar-19.
//

//
// Created by Vlad on 17-Mar-19.
//
#include "SpecificTestToMedicine.h"
#include <assert.h>

void performTests()
{
    // ############### TESTING ADDITION A LOT ################
    VectorList *medicines;
    VectorCreate(&medicines);
    Medication medication;

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Paracetamol");
    medication.concentration = 10;
    medication.price = 10;
    medication.quantity = 100;
    assert(addToEndController(medicines, medication)==0);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Codein");
    medication.concentration = 20;
    medication.price = 40;
    medication.quantity = 50;
    assert(addToEndController(medicines,medication)==0);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Vicodin");
    medication.concentration = 30;
    medication.price = 60;
    medication.quantity = 35;
    assert(addToEndController(medicines, medication)==0);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Aspirin");
    medication.concentration = 15;
    medication.price = 45;
    medication.quantity = 55;
    assert(addToEndController(medicines, medication)==0);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Creatine");
    medication.concentration = 90;
    medication.price = 20;
    medication.quantity = 123;
    assert(addToEndController(medicines, medication)==0);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Beta-Alanyne");
    medication.concentration = 30;
    medication.price = 10;
    medication.quantity = 15;
    assert(addToEndController(medicines, medication)==0);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Caffeine");
    medication.concentration = 100;
    medication.price = 30;
    medication.quantity = 200;
    assert(addToEndController(medicines, medication)==0);


    // ############### TESTING ADDITION OF DUPLICATE ################
    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Caffeine");
    medication.concentration = 100;
    medication.price = 30;
    medication.quantity = 200;
    assert(addToEndController(medicines, medication)!=0);
    free(medication.name);


    // ############### TESTING REMOVAL ################

    //Test if this is the first element
    assert(getValueByIndexController(medicines,0,&medication)==0);
    assert(strcmp(medication.name,"Paracetamol") == 0);
    assert(medication.concentration == 10);
    assert(medication.quantity == 100);
    assert(medication.price == 10);
    free(medication.name);

    assert(removeByIndexController(medicines,0)==0);

    assert(getValueByIndexController(medicines,0,&medication)==0);
    assert(strcmp(medication.name,"Paracetamol") != 0 || medication.concentration != 10);
    free(medication.name);

    //new first element
    assert(getValueByIndexController(medicines,0,&medication)==0);
    assert(strcmp(medication.name,"Codein")==0);
    assert(medication.concentration == 20);
    assert(medication.price == 40);
    assert(medication.quantity == 50);
    free(medication.name);

    // ############### TESTING UPDATING ################

    //modify Aspirin with 15 to "Batman"
    modifyNameController(medicines,"Aspirin",15,"Batman");
    assert(getValueByIndexController(medicines,2,&medication) ==0 );
    assert(strcmp(medication.name,"Batman") == 0);
    free(medication.name);

    modifyConcentrationController(medicines,"Batman",15,73);
    assert(getValueByIndexController(medicines,2,&medication) ==0 );
    assert(medication.concentration == 73);
    free(medication.name);

    modifyQuantityController(medicines,"Batman",73,9999);
    assert(getValueByIndexController(medicines,2,&medication) ==0 );
    assert(medication.quantity = 9999);
    free(medication.name);

    modifyPriceController(medicines,"Batman",73,8888);
    assert(getValueByIndexController(medicines,2,&medication) ==0 );
    assert(medication.price = 8888);
    free(medication.name);

    VectorDestroyElementNames(medicines);
    VectorDestroy(&medicines);
}

void testUndoRedo()
{
    VectorList *medicines;
    VectorCreate(&medicines);
    Operations *operations;
    createOperations(&operations,medicines);
    Medication medication;

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Paracetamol");
    medication.concentration = 10;
    medication.price = 10;
    medication.quantity = 100;
    assert(addToEndController(medicines, medication)==0);
    addOperationToList(operations,medicines);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Codein");
    medication.concentration = 20;
    medication.price = 40;
    medication.quantity = 50;
    assert(addToEndController(medicines,medication)==0);
    addOperationToList(operations,medicines);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Vicodin");
    medication.concentration = 30;
    medication.price = 60;
    medication.quantity = 35;
    assert(addToEndController(medicines, medication)==0);
    addOperationToList(operations,medicines);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Aspirin");
    medication.concentration = 15;
    medication.price = 45;
    medication.quantity = 55;
    assert(addToEndController(medicines, medication)==0);
    addOperationToList(operations,medicines);

    medication.name = (char *) malloc(sizeof(char)*100);
    strcpy(medication.name,"Creatine");
    medication.concentration = 90;
    medication.price = 20;
    medication.quantity = 123;
    assert(addToEndController(medicines, medication)==0);
    addOperationToList(operations,medicines);

    assert(operations->numberOfOperations==5);

    undoOperation(operations,medicines);
    assert(medicines->numberOfElements==4);
    assert(getValueByIndex(medicines,0,&medication)==0);
    assert(strcmp(medication.name,"Paracetamol")==0);
    free(medication.name);


    assert(getValueByIndex(medicines,3,&medication)==0);
    assert(strcmp(medication.name,"Aspirin")==0);
    free(medication.name);

    undoOperation(operations,medicines);
    assert(medicines->numberOfElements==3);
    assert(getValueByIndex(medicines,2,&medication)==0);
    assert(strcmp(medication.name,"Vicodin")==0);
    free(medication.name);

    undoOperation(operations,medicines);
    assert(medicines->numberOfElements==2);
    assert(getValueByIndex(medicines,1,&medication)==0);
    assert(strcmp(medication.name,"Codein")==0);
    free(medication.name);

    undoOperation(operations,medicines);
    assert(medicines->numberOfElements==1);
    assert(getValueByIndex(medicines,0,&medication)==0);
    assert(strcmp(medication.name,"Paracetamol")==0);
    free(medication.name);

    undoOperation(operations,medicines);
    assert(medicines->numberOfElements==0);

    assert(undoOperation(operations,medicines)==-1);

    redoOperation(operations,medicines);
    assert(medicines->numberOfElements==1);
    assert(getValueByIndex(medicines,0,&medication)==0);
    assert(strcmp(medication.name,"Paracetamol")==0);
    free(medication.name);

    redoOperation(operations,medicines);
    assert(medicines->numberOfElements==2);
    assert(getValueByIndex(medicines,1,&medication)==0);
    assert(strcmp(medication.name,"Codein")==0);
    free(medication.name);

    redoOperation(operations,medicines);
    assert(medicines->numberOfElements==3);
    assert(getValueByIndex(medicines,2,&medication)==0);
    assert(strcmp(medication.name,"Vicodin")==0);
    free(medication.name);

    redoOperation(operations,medicines);
    assert(medicines->numberOfElements==4);
    assert(getValueByIndex(medicines,3,&medication)==0);
    assert(strcmp(medication.name,"Aspirin")==0);
    free(medication.name);

    redoOperation(operations,medicines);
    assert(medicines->numberOfElements==5);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(strcmp(medication.name,"Creatine")==0);
    free(medication.name);

    assert(redoOperation(operations,medicines)==-1);

    assert(modifyNameController(medicines,"Creatine",90,"BoomAnine")==0);
    addOperationToList(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(strcmp(medication.name,"BoomAnine")==0);
    free(medication.name);

    assert(modifyConcentrationController(medicines,"BoomAnine",90,85)==0);
    addOperationToList(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(medication.concentration==85);
    free(medication.name);

    assert(modifyQuantityController(medicines,"BoomAnine",85,999)==0);
    addOperationToList(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(medication.quantity==999);
    free(medication.name);

    assert(modifyPriceController(medicines,"BoomAnine",85,888)==0);
    addOperationToList(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(medication.price==888);
    free(medication.name);

    undoOperation(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(medication.price==20);
    free(medication.name);

    undoOperation(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(medication.quantity==123);
    free(medication.name);

    undoOperation(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(medication.concentration==90);
    free(medication.name);

    undoOperation(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(strcmp(medication.name,"Creatine")==0);
    free(medication.name);

    redoOperation(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(strcmp(medication.name,"BoomAnine")==0);
    free(medication.name);

    redoOperation(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(medication.concentration == 85);
    free(medication.name);

    redoOperation(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(medication.quantity == 999);
    free(medication.name);

    redoOperation(operations,medicines);
    assert(getValueByIndex(medicines,4,&medication)==0);
    assert(medication.price == 888);
    free(medication.name);

    assert(redoOperation(operations,medicines)==-1);

    destroyOperations(&operations);
    VectorDestroyElementNames(medicines);
    VectorDestroy(&medicines);


}
