var app = app || {};

app.HopView = Backbone.View.extend({
    tagName: 'div',
    className: 'hopContainer',
    template: _.template( $('#hopTemplate').html() ),

    events: {
        'click .delete': 'deleteHop'
    },

    deleteHop: function() {
        // Delete model
        this.model.destroy();

        // Delete view
        this.remove();
    },

    render: function() {
        // tmpl is a function that takes a JSON object and returns html

        // this.el is what we defined in tagName. use $el to get access
        // to jQuery html() function
        this.$el.html( this.template( this.model.toJSON() ));
        this.$el.addClass('col-md-3');

        return this;
    }
});