curl -X POST localhost:9090/transactions



-- DROP TRIGGER IF EXISTS t ON ledger_entries;

-- DROP FUNCTION IF EXISTS t_function;

CREATE OR REPLACE FUNCTION t_function()
RETURNS trigger
LANGUAGE plpgsql
AS
$function$
declare
begin
insert into transaction_events(transaction_id) values(NEW.id);

    return NEW;
end;
$function$






DROP TRIGGER IF EXISTS t ON "transaction";
create trigger t
before insert
on "transaction"
for each row
execute procedure t_function();



create table transaction_events(id bigserial, transaction_id integer);


id,txnRefNumber,amount,type
1,u123,100,sale



10000 records, Chunk Size = 10
[COMPLETED] in 9s120ms
[COMPLETED] in 6s608ms
[COMPLETED] in 6s613ms


10000 records, Chunk Size = 1000
[COMPLETED] in 3s509ms
[COMPLETED] in 1s617ms
[COMPLETED] in 1s299ms


10000 records, Chunk Size = 1000, set id as null
[COMPLETED] in 2s424ms
[COMPLETED] in 1s393ms
[COMPLETED] in 1s113ms


10000 records, Chunk Size = 1000, set id as null, Multithreading 10 threads
[COMPLETED] in 2s347ms
[COMPLETED] in 1s141ms
[COMPLETED] in 1s59ms


10000 records, Chunk Size = 1000, set id as null, Multithreading 8 threads
[COMPLETED] in 1s554ms
[COMPLETED] in 1s67ms
[COMPLETED] in 880ms



### TODO

Read from Outbox

ItemProcess  8ms delay

Writer
   - Update
   - Delete 


