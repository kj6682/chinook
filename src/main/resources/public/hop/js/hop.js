var app = app || {};

app.Hop = Backbone.Model.extend({
    defaults: {
        title: 'No title',
        author: 'Unknown',
        location: 'Unknown',
        type: 'BOOK'
    }
});