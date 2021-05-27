// Copyright 2018 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:dog_shelter_management_app/dog_add_edit_route.dart';
import 'package:dog_shelter_management_app/dog_entity.dart';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Welcome to Flutter',
      home: MainScaffold(),
    );
  }
}

class MainScaffold extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var dogListHolder = DogListHolder();

    return Scaffold(
      appBar: AppBar(
        title: Text('Dog Shelter Management System'),
      ),
      body: Center(
        child: dogListHolder,
      ),
    );
  }
}

class DogListHolder extends StatefulWidget {
  @override
  _DogListHolderState createState() => _DogListHolderState();
}

class _DogListHolderState extends State<DogListHolder> {
  var list = [
    Dog.withParameters(1, "Azorel", 1, "Cute", "Race"),
    Dog.withParameters(2, "Catel", 2, "Cute2", "Race2"),
    Dog.withParameters(3, "Caine", 3, "Cute3", "Race3"),
    Dog.withParameters(4, "Etc", 4, "Cute4", "Race4"),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _buildDogList(),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {
          onPressedActionButton(context);
        },
        icon: Icon(Icons.add),
        label: Text("Add"),
      ),
    );
  }

  Future<void> onPressedActionButton(BuildContext context) async {
    await Navigator.push(
            context,
            new MaterialPageRoute(
                builder: (context) =>
                    new AddEditDog("Create Dog", generateDogId(), null)))
        .then((value) => setState(() {
              if (value != null) {
                list.add(value['dog']);
              }
            }));
  }

  Widget _buildDogList() {
    return ListView.builder(
      itemCount: list.length,
      padding: EdgeInsets.all(16.0),
      itemBuilder: _getSelectedDog,
    );
  }

  Widget _getSelectedDog(BuildContext context, int index) {
    return GestureDetector(
        onTap: () {
          onTapAction(context, index);
        },
        onLongPress: () {
          onLongPressAction(context, index);
        },
        onHorizontalDragUpdate: (details) {
          onPanUpdateAction(context, index);
        },
        child: Container(
          margin: EdgeInsets.symmetric(vertical: 4),
          child: _buildRow(list[index]),
        ));
  }

  onTapAction(BuildContext context, int index) {
    return showDialog(
        context: context,
        builder: (_) => new AlertDialog(
              title: new Text("Dog details"),
              content: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    Text("Name: " + list[index].name),
                    Text("Age: " + list[index].age.toString()),
                    Text("Race: " + list[index].race),
                    Text("Description: " + list[index].description)
                  ]),
              actions: <Widget>[
                FlatButton(
                  child: Text('Close me!'),
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                )
              ],
            ));
  }

  onLongPressAction(BuildContext context, int index) async {
    await Navigator.push(
            context,
            new MaterialPageRoute(
                builder: (context) =>
                    new AddEditDog("Edit Dog", list[index].id, list[index])))
        .then((value) => setState(() {
              if (value != null) {
                list[index] = value['dog'];
              }
            }));
  }

  onPanUpdateAction(BuildContext context, int index) {
    // set up the button
    Widget okButton = FlatButton(
      child: Text("OK"),
      onPressed: () {
        setState(() {
          list.removeAt(index);
        });
        Navigator.of(context).pop();
      },
    ); // set up the AlertDialog
    AlertDialog alert = AlertDialog(
      title: Text("Delete dog"),
      content: Text("Are you sure you want to delete?"),
      actions: [
        okButton,
      ],
    ); // show the dialog
    return showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  Widget _buildRow(Dog dog) {
    return ListTile(
      title: Text(
        dog.name,
      ),
    );
  }

  //inefficient, but irrelevant for simple demonstrations
  //IRL we would use databases which would generate this themselves
  int generateDogId() {
    for (int i = 0; i <= list.length; i++) {
      var existsDogWithId =
          list.firstWhere((element) => element.id == i, orElse: () => null) !=
              null;

      //if there is no dog with that id (i.e the above returned false), we return the current id as it is available)
      if (!existsDogWithId) return i;
    }
    return list.length;
  }
}
