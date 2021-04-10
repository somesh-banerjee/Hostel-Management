const express = require("express");
const bodyParser = require("body-parser");
const firebase = require("firebase");
// const admin = require('firebase-admin');
// const request = require("request");

const app = express();
app.use(bodyParser.urlencoded({ extented: true }));
app.use(express.static("public"));


const firebaseConfig = {
    apiKey: "",
    authDomain: "",
    projectId: "",
    storageBucket: "",
    messagingSenderId: "",
    appId: ""
};
const db = firebase.initializeApp(firebaseConfig);
const firestore = firebase.firestore();
var user;

// admin.initializeApp();



async function getData(req,res) {
    const snapshot = await firestore.collection('usersAll').get();
    snapshot.forEach((doc) => {
        console.log(doc.id, '=>', doc.data());
    });
}

function logOut() {
    firebase.auth().signOut().then(() => {
        // Sign-out successful.
    }).catch((error) => {
        // An error happened.
    });
}


async function addData(req,res) {
    const data = req.body;
    try {
        await firestore.collection('usersAll').doc().set(data);
        // res.send('Record saved successfuly');
        res.redirect('/hostelData');
        // setTimeout(function(res,req){ res.sendFile(__dirname + '/hostelData') }, 2000);
    } catch (error) {
        res.status(400).send(error.message);
    }

}

app.get("/signIn", function (req, res) {
    res.sendFile(__dirname + "/signIn.html");
});

app.get("/", function (req, res) {
    var user = firebase.auth().currentUser;
      if (user !== null) {
        req.user = user;
        res.redirect('/hostelData');
      } else {
        res.redirect('/signIn');
      }
});


//See all student record
// app.get('/hostelData', (req, res) => {
// 	student.find({}, (err, flights) => {
// 		if(err){
// 			console.log(err);
// 		} else {
// 			res.render('hostelData', {students: students});
// 		}
// 	});
// });

app.post("/", function (req, res) {
    console.log(req.body);
    var email = req.body.Email;//"test@example.com";
    var password = req.body.pass;//"password"; 

    firebase.auth().signInWithEmailAndPassword(email, password)
        .then((userCredential) => {
            // Signed in
            user = userCredential.user;
            // console.log(user);
            res.sendFile(__dirname + "/hostelData.html");
            // res.send("Welcome to Hostel Management");
            // addData(req,res);
            getData(req,res);
        })
        .catch((error) => {
            var errorCode = error.code;
            var errorMessage = error.message;
            res.redirect("/signIn");
            console.log("Try Again");
        });
});

app.post("/hostelData", function (req, res) {
    console.log(req.body);
    try {
        addData(req,res);
        // res.redirect(__dirname +'/hostelData.html');
    } catch (error) {
        console.log(error);
    }
});

app.get('/new', (req, res) => {
    res.sendFile(__dirname + "/new.html");
})


app.get('/logout', function (req, res) {
    logOut();
    res.redirect("/");
});

app.listen(3000, function () {
    console.log("Server started successfully on port 3000.");
});
