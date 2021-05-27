import 'package:dog_shelter_management_app/dog_entity.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AddEditDog extends StatelessWidget {
  String title;
  int dogId;
  Dog dog;

  AddEditDog(String title, int dogId, Dog currentDog) {
    this.title = title;
    this.dogId = dogId;
    this.dog = currentDog;
  }

  @override
  Widget build(BuildContext context) {
    final nameController = TextEditingController();
    final ageController = TextEditingController();
    final raceController = TextEditingController();
    final descriptionController = TextEditingController();

    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: new DogCustomForm(dogId, dog),
    );
  }
}

// Define a custom Form widget.
class DogCustomForm extends StatefulWidget {
  int dogId;
  Dog currentDog;

  DogCustomForm(this.dogId, this.currentDog);

  @override
  DogFormState createState() {
    return DogFormState(dogId, currentDog);
  }
}

// Define a corresponding State class.
// This class holds data related to the form.
class DogFormState extends State<DogCustomForm> {
  // Create a global key that uniquely identifies the Form widget
  // and allows validation of the form.
  //
  // Note: This is a `GlobalKey<FormState>`,
  // not a GlobalKey<MyCustomFormState>.
  var nameController;
  var ageController;
  var raceController;
  var descriptionController;

  int dogId;
  Dog currentDog;

  DogFormState(this.dogId, this.currentDog) {
    this.nameController = new TextEditingController(
      text: (currentDog != null) ? currentDog.name : "",
    );
    this.ageController = new TextEditingController(
      text: (currentDog != null) ? currentDog.age.toString() : "",
    );
    this.raceController = new TextEditingController(
      text: (currentDog != null) ? currentDog.race : "",
    );
    this.descriptionController = new TextEditingController(
      text: (currentDog != null) ? currentDog.description : "",
    );
  }

  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return Form(
        key: _formKey,
        child: new Column(children: <Widget>[
          new Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Container(
                    width: MediaQuery.of(context).size.width * 0.3,
                    alignment: Alignment.centerLeft,
                    child: Text("Name")),
                Container(
                    width: MediaQuery.of(context).size.width * 0.7,
                    alignment: Alignment.center,
                    child: TextFormField(controller: nameController)),
              ]),
          new Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Container(
                    width: MediaQuery.of(context).size.width * 0.3,
                    alignment: Alignment.centerLeft,
                    child: Text("Age")),
                Container(
                    width: MediaQuery.of(context).size.width * 0.7,
                    alignment: Alignment.center,
                    child: TextFormField(
                        keyboardType: TextInputType.number,
                        controller: ageController)),
              ]),
          new Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Container(
                    width: MediaQuery.of(context).size.width * 0.3,
                    alignment: Alignment.centerLeft,
                    child: Text("Description")),
                Container(
                    width: MediaQuery.of(context).size.width * 0.7,
                    alignment: Alignment.center,
                    child: TextFormField(controller: descriptionController)),
              ]),
          new Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Container(
                    width: MediaQuery.of(context).size.width * 0.3,
                    alignment: Alignment.centerLeft,
                    child: Text("Race")),
                Container(
                    width: MediaQuery.of(context).size.width * 0.7,
                    alignment: Alignment.center,
                    child: TextFormField(controller: raceController)),
              ]),
          new Row(crossAxisAlignment: CrossAxisAlignment.start, children: [
            new ElevatedButton(
              child: Text("Submit"),
              onPressed: () {
                var name = nameController.text;
                var age = ageController.text;
                var race = raceController.text;
                var description = descriptionController.text;
                if (name.isNotEmpty &&
                    age.isNotEmpty &&
                    race.isNotEmpty &&
                    description.isNotEmpty) {
                  if (int.parse(age) is int) {
                    var newDog = Dog.withParameters(
                        dogId, name, int.parse(age), race, description);
                    Navigator.of(context).pop({"dog": newDog});
                  } else {
                    errorDialog(context);
                  }
                } else {
                  errorDialog(context);
                }
              },
            )
          ])
        ]));
  }

  errorDialog(BuildContext context) {
    return showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text("Error"),
            content: Text("Please input valid data"),
            actions: <Widget>[
              FlatButton(
                child: Text('Close me!'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }
}
