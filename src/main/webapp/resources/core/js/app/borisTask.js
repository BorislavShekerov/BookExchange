function computeRecurrenceTable(input) {
    var map = new Object(); // will be storing all the recurrences here
    var logicallySplitInput = input.split(/[-:/"]/); //splittinh the input into logical phrases 
    var accumulatedPhraseArr = [];

    for (i = 0; i < logicallySplitInput.length; i++) {
        var phrase = logicallySplitInput[i];

        if (phrase) {
            var words = phrase.split(" ");

            var accumulatedPhraseArr = [];
            var accumulatedPhrase = "";
            for (j = 0; j < words.length; j++) {

                var currentWord = words[j];

                if (currentWord === "The") {
                    console.log("The");
                }
                if (accumulatedPhraseArr.length < 5) {
                    putEntryInMap(map, currentWord);
                    accumulatedPhraseArr.push(currentWord);

                    if (accumulatedPhraseArr.length > 1) {
                        computePhraseVariatios(map, accumulatedPhraseArr);
                    }
                } else if (accumulatedPhraseArr.length == 5) {
                    putEntryInMap(map, currentWord);
                    accumulatedPhraseArr.splice(0, 1);
                    accumulatedPhraseArr.push(currentWord);

                    computePhraseVariatios(map, accumulatedPhraseArr);

                }
            }
        }
    }

    var x;
    var phrasesArray = [];
    for (x in map) {
        var phrase = {
            phrase: x,
            properties: map[x]
        }
        phrasesArray.push(phrase);
    }

    phrasesArray.sort(compare);

    console.log("Top 20 of the most repetative phrases");
    for (j = 0; j < 20; j++) {
        console.log('"' + phrasesArray[j].phrase + '" - a ' + phrasesArray[j].properties.wordCount + ' word phrase which is in the result with the weight ' + phrasesArray[j].properties.weight + '(occured ' + phrasesArray[j].properties.occurences + ' times)');
    }
}


function compare(a, b) {
    var overallWeightA = a.properties.occurences * a.properties.wordCount;
    a.properties.weight = overallWeightA;
    var overallWeightB = b.properties.occurences * b.properties.wordCount;
    b.properties.weight = overallWeightB;
    if (overallWeightA > overallWeightB)
        return -1;
    if (overallWeightA < overallWeightB)
        return 1;
    return 0;
}

function computePhraseVariatios(map, accumulatedPhraseArr) {
    var accumulatedPhrase = "";
    for (k = 0; k < accumulatedPhraseArr.length; k++) {
        accumulatedPhrase = accumulatedPhrase.concat(accumulatedPhraseArr[k]);
        if (k < accumulatedPhraseArr.length - 1) accumulatedPhrase = accumulatedPhrase.concat(" ");

        var internalAccumulatedPhrase = ""
        if (accumulatedPhraseArr.length > 2 && accumulatedPhraseArr.length - k > 2) {
            for (z = k + 1; z < accumulatedPhraseArr.length; z++) {
                internalAccumulatedPhrase = internalAccumulatedPhrase.concat(accumulatedPhraseArr[z]);
                if (z < accumulatedPhraseArr.length - 1) internalAccumulatedPhrase = internalAccumulatedPhrase.concat(" ");
            }
            $scope.putEntryInMap(map, internalAccumulatedPhrase);
        }
    }
    $scope.putEntryInMap(map, accumulatedPhrase);
}

function putEntryInMap(map, phrase) {
    if (map[phrase]) {
        var mapEntry = map[phrase];
        mapEntry.occurences += 1;

    } else {
        var numOfWords = phrase.split(' ').length;
        map[phrase] = {
            occurences: 1,
            wordCount: numOfWords
        }
    }
}

function solveProblem() {
    var input = '"The Hunger Games:Mockingjay Part 3 Monty Python and The Holy Grail Schindler\'s List:A True Story The Good,The Bad,and The Ugly(1996) The Lord of The Rings:"The Return of The Kings" The Lord of The Rings:"The Fellowship of The King" One Flew Over The Cuckoo\'s Nest The Lord of The Rings:"The Two Towers" Forest Gump Star Wars:Episode V-The Empire Strikes Back(1980) The Silence of The Lambs';
    computeRecurrenceTable(input);
};