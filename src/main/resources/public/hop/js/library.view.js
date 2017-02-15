var app = app || {};

app.LibraryView = Backbone.View.extend({
    el: '#hops',

    events:{
        'click #add':'addHop'
    },


    initialize: function() {
        this.collection = new app.Library();
        this.collection.fetch({reset: true});
        this.render();

        this.listenTo( this.collection, 'add', this.renderHop );
        this.listenTo( this.collection, 'reset', this.render );
    },

    addHop: function( e ) {
        e.preventDefault();

        var formData = {};

        $( '#addHop div' ).children( 'input' ).each( function( i, el ) {
            if( $( el ).val() != '' ){
                formData[ el.id ] = $( el ).val();
            }
             // Clear input field value
             $( el ).val('');
        });

        var hop = new app.Hop( formData )
        this.collection.add( hop );
        hop.save();


    },

    // render library by rendering each book in its collection
    render: function() {
        this.collection.each(function( item ) {
            this.renderHop( item );
        }, this );
    },

    // render a book by creating a BookView and appending the
    // element it renders to the library's element
    renderHop: function( item ) {
        var hopView = new app.HopView({
            model: item
        });
        this.$el.append( hopView.render().el );
    }
});