use pokedex;

create table pokemon (
    -- Official pokemon number is also the table key 
    pokemon_num integer primary key, 
    name varchar(50) unique, 
    types varchar(75), 
    weight integer, 
    -- Whole part is feet, decimal part is inches 
    height decimal(5, 2), 
    call_sound varbinary(1000000), -- Binary sound file 
    text_description varchar(500), -- Displayed 
    read_description varchar(500) -- Read by app 
);