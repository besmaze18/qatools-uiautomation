//#region finders
var finders = {};
finders.xpath = function (xpathToExecute) {
  var result = [];
  if(document.evaluate) {
    var nodesSnapshot = document.evaluate(xpathToExecute, document, null,
        XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
    for (var i = 0; i < nodesSnapshot.snapshotLength; i++) {
      result.push(nodesSnapshot.snapshotItem(i));
    }
    return result;
  }
};
finders.cssselector = function (cssSelector) {
  return document.querySelector(cssSelector);
};
finders.id = function (idSelector) {
  return document.getElementById(idSelector);
};
finders.name = function (nameSelector) {
  return document.getElementsByName(nameSelector);
};
finders.tagname = function (tagName) {
  return document.getElementsByTagName(tagName);
};

//#endregion

function isElementVisible(element) {
  var style = null;
  if (window.getComputedStyle) {
    style = window.getComputedStyle(element);
  } else {
    style = element.currentStyle;
  }
  var isNotTransparent = style.opacity != 0;
  return style.display !== "none" && isNotTransparent;
}

//awaits for certain element to appear
//findBy - selector type
//findWith - selector query
//check - method that checks if element is ready
function awaiter(findBy, findWith, check, options) {
  if (!options) {
    throw "Cannot execute awaiter without options!";
  }
  if (!options.ReturnCallback) {
    throw "Wrong format of script - cannot return control back to test without return callback";
  }

  options.waitInterval = (options.waitInterval !== undefined)
      ? options.waitInterval : 200;
  options.attempments = (options.attempments !== undefined)
      ? options.attempments : 50;

  //main body
  var findMethod = finders[findBy.toLowerCase()];
  if (!findMethod) {
    throw "Cannot search element by => " + findBy;
  }

  function checkIteration(attempts) {
    try {
      if (attempts > options.attempments) {
        return options.ReturnCallback({Result: false});
      }
      attempts++;
      var found = findMethod(findWith);
      if (!found || found.length < 1) {
        return setTimeout(function () {
          checkIteration(attempts);
        }, options.waitInterval);
      }
      if (found.length) {
        found = found[0];
      }
      if (check(found)) {
        return options.ReturnCallback({Result: true});
      } else {
        return setTimeout(function () {
          checkIteration(attempts);
        }, options.waitInterval);
      }
    } catch (e) {
      return options.ReturnCallback({Result: e.toString(), Fail: true});
    }
  }

  checkIteration(0);
  //end of main body
}