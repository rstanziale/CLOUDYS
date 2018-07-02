-- CREAZIONE TIPI
CREATE OR REPLACE
TYPE RAMTY AS OBJECT (
  ID INTEGER,
  CAPACITA INTEGER
);
/

CREATE OR REPLACE
TYPE HDDTY AS OBJECT (
  ID INTEGER,
  CAPACITA INTEGER
);
/

CREATE OR REPLACE
TYPE PROCESSORETY AS OBJECT (
  MODELLO VARCHAR(25),
  CORE INTEGER
);
/

CREATE OR REPLACE TYPE REF_HDDTY AS OBJECT
( HDD ref HDDTY
);
/

CREATE OR REPLACE TYPE HDDNT AS TABLE OF ref_hddty;
/

CREATE OR REPLACE
TYPE SERVERTY AS OBJECT (
  ID INTEGER,
  RAM REF RAMTY,
  PROCESSORE REF PROCESSORETY,
  HDD HDDNT
);
/

CREATE OR REPLACE
TYPE SOFTWARETY AS OBJECT (
  ID INTEGER,
  NOME VARCHAR(30),
  COSTO DECIMAL(7,2)
) NOT FINAL;
/

CREATE OR REPLACE
TYPE APPTY UNDER SOFTWARETY (
  LICENZA VARCHAR(12),
  VERSIONE DECIMAL(4,2)
);
/

CREATE OR REPLACE TYPE REF_APPTY AS OBJECT
( APP ref APPTY
);
/

CREATE OR REPLACE TYPE APPNT AS TABLE OF ref_appty;
/

CREATE OR REPLACE
TYPE OSTY UNDER SOFTWARETY (
  app appnt
);
/

CREATE OR REPLACE TYPE REF_SOFTWARETY AS OBJECT
( SOFTWARE ref SOFTWARETY
);
/

CREATE OR REPLACE TYPE SOFTWARENT AS TABLE OF ref_softwarety;
/

CREATE OR REPLACE TYPE REF_SERVERTY AS OBJECT
( SERVER ref SERVERTY
);
/

CREATE OR REPLACE TYPE SERVERNT AS TABLE OF ref_serverty;
/

create or replace TYPE VMTY AS OBJECT (
  ID INTEGER,
  HDD INTEGER,
  RAM INTEGER,
  PROCESSORE INTEGER,
  DATACREAZIONE TIMESTAMP,
  COSTO DECIMAL(5,2),
  SOFTWARE SOFTWARENT,
  SERVER SERVERNT
)NOT FINAL;
/

create or replace TYPE VMDISTRUTTATY UNDER VMTY (
  DATADISTRUZIONE TIMESTAMP
);
/

CREATE OR REPLACE TYPE REF_VMTY AS OBJECT
( VM ref VMTY
);
/

CREATE OR REPLACE TYPE VMNT AS TABLE OF ref_vmty;
/

CREATE OR REPLACE TYPE FATTURATY AS OBJECT (
  CODICE INTEGER,
  DATAEMISSIONE DATE,
  IMPORTO DECIMAL(7,2),
  VM VMNT
);
/

create or replace TYPE CLIENTETY AS OBJECT (
  CF varchar(16),
  NOME VARCHAR(20),
  COGNOME VARCHAR(20),
  EMAIL VARCHAR(30),
  CITTA VARCHAR(20),
  BONUS INTEGER,
  VM VMNT
);
/

CREATE OR REPLACE TYPE REF_CLIENTETY AS OBJECT(
  CLIENTE ref CLIENTETY
);
/

CREATE OR REPLACE TYPE CLIENTENT AS TABLE OF ref_clientety;
/

ALTER TYPE clientety 
   ADD ATTRIBUTE (clientiportati clientent) CASCADE;
/

ALTER TYPE fatturaty 
   ADD ATTRIBUTE (cliente ref clientety) CASCADE;
/

ALTER TYPE vmty 
   ADD ATTRIBUTE (cliente ref clientety) CASCADE;
/

ALTER TYPE serverty 
   ADD ATTRIBUTE (macchinevirtuali vmnt) CASCADE;
/

ALTER TYPE VMTY
   ADD ATTRIBUTE (FATTURA REF FATTURATY) CASCADE;
/

create or replace TYPE USERSTY AS OBJECT
( email varchar(30),
  pw varchar(30),
  ruolo varchar(1),
  dati_utente ref clientety
)
/

-- CREAZIONE TABELLE
create table processore of processorety(
  primary key(modello, core)
);
/

create table RAM of ramty(
  id primary key,
  capacita not null
);
/

create table hdd of hddty(
  id primary key,
  capacita not null
);
/

create table server of serverty(
  id primary key
)nested table hdd store as hddnt_tab_server, nested table macchinevirtuali store as vmnt_tab_server;
/

create table os of osty(
  id primary key,
  nome not null,
  costo not null
)nested table app store as appnt_tab_os;
/

create table app of appty(
  id primary key,
  nome not null,
  costo not null,
  licenza not null,
  versione not null
);
/

create table fattura of fatturaty(
  codice primary key,
  dataemissione not null,
  importo not null
)nested table vm store as vmnt_tab_fattura;
/

create table cliente of clientety(
  cf primary key,
  nome not null,
  cognome not null,
  citta not null,
  email not null
)nested table vm store as vmnt_tab_cliente, nested table clientiportati store as clientiportatint_tab_cliente;
/

create table vmcreata of vmty(
  id primary key,
  hdd not null,
  ram not null,
  processore not null,
  datacreazione not null,
  costo not null
)nested table software store as softwarent_tab_vmcreata, nested table server store as servernt_tab_vmcreata;
/

create table vmdistrutta of vmdistruttaty(
  id primary key,
  hdd not null,
  ram not null,
  processore not null,
  datacreazione not null,
  datadistruzione not null,
  costo not null
)nested table software store as softwarent_tab_vmdistrutta, nested table server store as servernt_tab_vmdistrutta;
/

create table users of usersty(
  email primary key,
  pw not null,
  ruolo not null
);
/

-- SEQUENZE E TRIGGER
CREATE SEQUENCE hdd_seq START WITH 1;
/

CREATE SEQUENCE ram_seq START WITH 1;
/

CREATE SEQUENCE server_seq START WITH 1;
/

CREATE SEQUENCE os_seq START WITH 1;
/

CREATE SEQUENCE app_seq START WITH 1;
/

CREATE SEQUENCE vmcreata_seq START WITH 1;
/

CREATE SEQUENCE vmdistrutta_seq START WITH 1;
/

CREATE SEQUENCE fattura_seq START WITH 1;
/

CREATE OR REPLACE TRIGGER hdd_bir 
BEFORE INSERT ON HDD 
FOR EACH ROW

BEGIN
  SELECT hdd_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER ram_bir 
BEFORE INSERT ON ram 
FOR EACH ROW

BEGIN
  SELECT ram_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER server_bir 
BEFORE INSERT ON server 
FOR EACH ROW

BEGIN
  SELECT server_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER os_bir 
BEFORE INSERT ON os 
FOR EACH ROW

BEGIN
  SELECT os_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER app_bir 
BEFORE INSERT ON app 
FOR EACH ROW

BEGIN
  SELECT app_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER vmcreata_bir 
BEFORE INSERT ON VMCREATA
FOR EACH ROW

BEGIN
  SELECT vmcreata_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER vmdistrutta_bir 
BEFORE INSERT ON VMDISTRUTTA
FOR EACH ROW

BEGIN
  SELECT vmdistrutta_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER fattura_bir 
BEFORE INSERT ON fattura
FOR EACH ROW

BEGIN
  SELECT fattura_seq.NEXTVAL
  INTO   :new.codice
  FROM   dual;
END;
/

-- TRIGGER
create or replace TRIGGER CONTROLLA_LUNG_CF_CLIENTE
BEFORE INSERT ON CLIENTE
FOR EACH ROW
DECLARE
    lunghezza number;
BEGIN
  lunghezza := LENGTH(:new.cf);
  if lunghezza < 16 then
    dbms_output.put_line('Attenzione: inserimento annullato! Codice fiscale non valido.');
    raise_application_error(-20000, 'Attenzione: inserimento annullato! Codice fiscale non valido.');
  end if;
END;
/

create or replace TRIGGER CONTROLLA_MULTIPLO_RAM
BEFORE INSERT ON RAM
FOR EACH ROW
BEGIN
  IF (MOD(:NEW.CAPACITA, 4) != 0) then
    dbms_output.put_line('Attenzione: inserimento annullato! Capacità RAM non valida.');
    raise_application_error(-20000, 'Attenzione: inserimento annullato! Capacità RAM non valida.');
  END IF;
END;
/

create or replace TRIGGER CONTROLLA_MULTIPLO_CORE
BEFORE INSERT ON PROCESSORE
FOR EACH ROW
BEGIN
  IF (MOD(:NEW.CORE, 2) != 0) then
    dbms_output.put_line('Attenzione: inserimento annullato! Valore Core non valido.');
    raise_application_error(-20000, 'Attenzione: inserimento annullato! Valore Core non valido.');
  END IF;
END;
/

create or replace TRIGGER CONTROLLA_MULTIPLO_HDD
BEFORE INSERT ON HDD
FOR EACH ROW
BEGIN
  IF (MOD(:NEW.CAPACITA, 64) != 0) then
    dbms_output.put_line('Attenzione: inserimento annullato! Capacità HDD non valida.');
    raise_application_error(-20000, 'Attenzione: inserimento annullato! Capacità HDD non valida.');
  END IF;
END;
/

create or replace TRIGGER EVITA_DUPLICATO_RAM
BEFORE INSERT ON RAM
FOR EACH ROW
DECLARE
  Cursor c is select capacita from ram;
  val RAM.CAPACITA%TYPE;
BEGIN
  open c;
  
  loop 
    fetch C into val;
    exit when c%notfound;
    if :new.capacita = val then
      dbms_output.put_line('Attenzione: inserimento annullato! Capacità RAM presente.');
      raise_application_error(-20000, 'Attenzione: inserimento annullato! Capacità RAM presente.');
    end if;
  end loop;
END;
/

create or replace TRIGGER EVITA_DUPLICATO_CORE
BEFORE INSERT ON PROCESSORE
FOR EACH ROW
DECLARE
  Cursor c is select CORE from PROCESSORE;
  val PROCESSORE.CORE%TYPE;
BEGIN
  open c;
  
  loop 
    fetch C into val;
    exit when c%notfound;
    if :new.core = val then
      dbms_output.put_line('Attenzione: inserimento annullato! Core presente.');
      raise_application_error(-20000, 'Attenzione: inserimento annullato! Core presente.');
    end if;
  end loop;
END;
/


create or replace TRIGGER CONTROLLA_EMAIL_DUPLICATA
BEFORE INSERT ON CLIENTE
FOR EACH ROW
DECLARE 
  CURSOR C IS SELECT EMAIL FROM cliente;
  E CLIENTE.EMAIL% TYPE;
BEGIN
  OPEN C;
  LOOP
    FETCH C INTO E;
    EXIT WHEN C%NOTFOUND;
    IF :NEW.EMAIL = E  then
    dbms_output.put_line('Attenzione: inserimento annullato! Email già presente.');
    raise_application_error(-20000, 'Attenzione: inserimento annullato! Email già presente.');
  END IF;
  END LOOP;
  CLOSE C;
END;
/

-- PROCEDURE
create or replace PROCEDURE AGGIORNA_BONUS
( email IN cliente.email%TYPE,
  emailAmico IN cliente.email%TYPE
) AS
  Cursor c is select email from cliente;
  val cliente.email%TYPE;
BEGIN
  IF email = emailamico then
    dbms_output.put_line('Attenzione: inserimento annullato! Core presente.');
    raise_application_error(-20000, 'Attenzione: inserimento annullato! Core presente.');
  end if;
  
  open c;
  loop
    fetch c into val;
    exit when c%notfound;
    if val = emailamico then
      UPDATE cliente
      set bonus = bonus + 30
      where email = emailamico;
    end if;
  end loop;
  close c;
END AGGIORNA_BONUS;
/

create or replace PROCEDURE AGGIORNA_CLIENTIPORTATI
( emailIscrivente IN cliente.email%TYPE,
  emailIscritto IN cliente.email%TYPE
) AS
  Cursor c is select email from cliente;
  
  cursor d is  select count(deref(cliente).nome) from table(
    select clientiportati from cliente where email = emailIscritto)NT;
  
  val cliente.email%TYPE;
  cli number(3);
BEGIN
  open c;
  loop
    fetch c into val;
    exit when c%notfound;
    if val = emailIscritto then
      open d;
      loop
        fetch d into cli;
        exit when d%notfound;
        IF cli = 0 THEN
          update cliente
          set clientiportati = clientent((ref_clientety((select ref(cc) from cliente cc where cc.email = emailiscrivente))))
          where email = emailiscritto;
        RETURN;
        else
          update cliente
          set clientiportati = clientiportati
            multiset union all
            clientent((ref_clientety((select ref(cc) from cliente cc where cc.email = emailIscrivente))))
          where email = emailIscritto;
        end if;
      end loop;
      close d;
    end if;
  end loop;
  close c;
  dbms_output.put_line('Aggiornamento clientiportati completato');
END AGGIORNA_CLIENTIPORTATI;
/

create or replace PROCEDURE AGGIUNGI_APPLICAZIONE_VM
( ap IN app.nome%TYPE, 
  vm IN vmcreata.id%type
) AS
cursor c is select nome from app;
nal app.nome%TYPE;
BEGIN
  open c;
  loop
    fetch c into nal;
    exit when c%notfound;
    if nal = ap then
      update vmcreata
      set software =  software
          multiset union all
          softwarent((ref_softwarety((select ref(a) from app a where a.nome = ap))))
      where id = vm;
      
      update vmcreata
      set costo = costo + ((select costo from app a where a.nome = ap))
      where id = vm;
    end if;
  end loop;
  close c;
END AGGIUNGI_APPLICAZIONE_VM;
/

create or replace
PROCEDURE DISTRUGGI_VM (
 IDVMCREATA VMCREATA.ID%TYPE
) AS
IDDISTRUTTA integer;
val integer;
codfat fattura.codice%type;
BEGIN 
  SELECT vmdistrutta_seq.nextval into IDDISTRUTTA  from dual;

  INSERT INTO VMDISTRUTTA VALUES (IDDISTRUTTA,
  (SELECT HDD FROM VMCREATA WHERE ID = idvmcreata), 
  (SELECT RAM FROM VMCREATA WHERE ID = idvmcreata),
  (SELECT PROCESSORE FROM VMCREATA WHERE ID = idvmcreata),
  (SELECT DATACREAZIONE FROM VMCREATA WHERE ID = idvmcreata),
  (SELECT COSTO FROM VMCREATA WHERE ID = idvmcreata),
  (SELECT SOFTWARE FROM VMCREATA WHERE ID = idvmcreata),
  (SELECT SERVER FROM VMCREATA WHERE ID = idvmcreata),
  (SELECT CLIENTE FROM VMCREATA WHERE ID = idvmcreata),
  (SELECT FATTURA FROM VMCREATA WHERE ID = idvmcreata), 
  (select systimestamp from dual));
  
  SELECT (DEREF(FATTURA)).CODICE  into codfat FROM VMCREATA WHERE ID = IDVMCREATA;
  
  select count(deref(vm).id) into val from table(
    select vm from fattura where codice = codfat)NT;
  
  if val = 0 then
      UPDATE FATTURA
      SET VM = VMNT (REF_VMTY((SELECT REF(V) FROM vmdistrutta V WHERE V.id = IDDISTRUTTA)))
      WHERE CODICE = (SELECT (DEREF(FATTURA)).CODICE FROM VMCREATA WHERE ID = IDVMCREATA);
  
  else
      UPDATE FATTURA
      SET VM = VM MULTISET UNION ALL VMNT (REF_VMTY((SELECT REF(V) FROM vmdistrutta V WHERE V.id = IDDISTRUTTA)))
      WHERE CODICE = (SELECT (DEREF(FATTURA)).CODICE FROM VMCREATA WHERE ID = IDVMCREATA);
  end if;
  
  DELETE FROM vmcreata WHERE ID = idvmcreata;
  
END DISTRUGGI_VM;
/

 
--FUNZIONI
create or replace FUNCTION GETIMPORTO_VMCREATA
( CFCLIENTE IN CLIENTE.CF%TYPE
) RETURN VMCREATA.COSTO%TYPE IS IMPORTOTOT VMCREATA.COSTO%TYPE;

  CURSOR C IS SELECT * FROM VMCREATA WHERE DEREF(CLIENTE).CF = CFCLIENTE;
  CLI C%ROWTYPE;
  
BEGIN
  IMPORTOTOT := 0;
  OPEN C;
  LOOP
    FETCH C INTO CLI;
    EXIT WHEN C%NOTFOUND;
    IMPORTOTOT := IMPORTOTOT + CLI.COSTO;
  END LOOP;
  CLOSE C;
  
  RETURN IMPORTOTOT;
  
END GETIMPORTO_VMCREATA;
/

create or replace FUNCTION GETIMPORTO_VMDISTRUTTA
( CFCLIENTE IN CLIENTE.CF%TYPE
) RETURN VMDISTRUTTA.COSTO%TYPE IS IMPORTOTOT VMDISTRUTTA.COSTO%TYPE;

  CURSOR C IS SELECT * FROM VMDISTRUTTA WHERE DEREF(CLIENTE).CF = CFCLIENTE;
  CLI C%ROWTYPE;
  
BEGIN
  IMPORTOTOT := 0;
  OPEN C;
  LOOP
    FETCH C INTO CLI;
    EXIT WHEN C%NOTFOUND;
    IMPORTOTOT := IMPORTOTOT + CLI.COSTO;
  END LOOP;
  CLOSE C;
  
  RETURN IMPORTOTOT;
  
END GETIMPORTO_VMDISTRUTTA;
/

create or replace
PROCEDURE AGGIORNA_IMPORTO_FATTURE
( cf_cliente IN cliente.cf%TYPE) AS

mese_corrente VARCHAR2(20);
cursor c is select * from fattura where to_char(dataemissione,'Mon')= mese_corrente and deref(cliente).cf = cf_cliente;

singola_fattura c%rowtype;
importo_vmcreata vmcreata.costo%type;
importo_vmdistrutta vmdistrutta.costo%type;
importo_totale vmdistrutta.costo%type;
num_fatture number;

BEGIN
  num_fatture:=0;
  SELECT SUBSTR(to_char(systimestamp, 'Month'),0,3) into mese_corrente from dual;
  SELECT count(codice) into num_fatture from fattura  where to_char(dataemissione,'Mon')= mese_corrente and deref(cliente).cf = cf_cliente ;
  dbms_output.put_line(num_fatture);
    

  if num_fatture > 0 then
      open c;
      loop
        fetch c into singola_fattura;
        exit when c%notfound;
        select GETIMPORTO_VMCREATA(cf_cliente) into importo_vmcreata from dual;
        select GETIMPORTO_VMDISTRUTTA(cf_cliente) into importo_vmdistrutta from dual;
        importo_totale := importo_vmcreata + importo_vmdistrutta + singola_fattura.importo;
            dbms_output.put_line(importo_totale);
    
        update fattura 
        set importo = importo_totale 
        where  codice = singola_fattura.codice;
    
      end loop;
      close c;
      
  else
        select GETIMPORTO_VMCREATA(cf_cliente) into importo_vmcreata from dual;
        select GETIMPORTO_VMDISTRUTTA(cf_cliente) into importo_vmdistrutta from dual;
        importo_totale := importo_vmcreata + importo_vmdistrutta;
        insert into fattura values(null,sysdate,importo_totale, ((select vm from cliente where cf = cf_cliente)),((select ref(c) from cliente c where cf = cf_cliente))); 
  end if;
END AGGIORNA_IMPORTO_FATTURE;
/

create or replace PROCEDURE ITER_CLIENTI AS
  CURSOR C IS SELECT CF FROM CLIENTE;
  CLI CLIENTE.CF%TYPE;
  NUMVM INTEGER;
BEGIN
  OPEN C;
  LOOP
    FETCH C INTO CLI;
    EXIT WHEN C%NOTFOUND;
      SELECT count(DEREF(NT.VM).ID) INTO NUMVM FROM TABLE(SELECT VM FROM CLIENTE WHERE CF = CLI)NT;
      IF NUMVM > 0 THEN
        AGGIORNA_IMPORTO_FATTURE(CLI);
      END IF;
  END LOOP;
  CLOSE C;
END ITER_CLIENTI;
/

--schedule & job
begin
	DBMS_SCHEDULER.CREATE_PROGRAM ('SCHEDULER_FATTURA','PLSQL_BLOCK','BEGIN ITER_CLIENTI; END;',0,TRUE);
  
	DBMS_SCHEDULER.CREATE_SCHEDULE ('MINUTO',systimestamp,'FREQ=MINUTELY',systimestamp+1);
  --QUESTO LO AVVIA
	DBMS_SCHEDULER.CREATE_JOB (job_name=>'AGGIORNA_FATTURE',program_name=>'SCHEDULER_FATTURA',schedule_name=>'MINUTO',enabled=>TRUE);
end;
/

--BEGIN
--  dbms_scheduler.drop_job(job_name => 'AGGIORNA_FATTURE');
--END;


create or replace PROCEDURE AGGIORNA_SERVER_VM
( IDSERVER IN SERVER.ID%TYPE
, IDVM IN VMCREATA.ID%TYPE
, T IN INTEGER
) AS
BEGIN

  IF T = 1 THEN
    UPDATE VMCREATA
    SET SERVER = SERVERNT(REF_SERVERTY((SELECT REF(S) FROM SERVER S WHERE S.ID = IDSERVER))) 
    WHERE ID = IDVM;
  ELSE
    UPDATE VMCREATA
    SET SERVER =   SERVER 
      MULTISET UNION ALL
      SERVERNT(REF_SERVERTY((SELECT REF(S) FROM SERVER S WHERE S.ID = IDSERVER))) 
    WHERE ID = IDVM;
  END IF;
  
END AGGIORNA_SERVER_VM;
/

create or replace PROCEDURE ASSEGNA_SERVER (
  IDVM IN VMCREATA.ID%TYPE) AS
  
  TROVATO INTEGER;
  NUM_VM INTEGER;
  NUM_VMFIGLIO INTEGER;
  NUM_VMCHECK INTEGER;
  
  CAPA_HDD_SERVER INTEGER;
  SER_RAM RAM.CAPACITA%TYPE;
  SER_CORE PROCESSORE.CORE%TYPE;
  
  CAPA_HDD_SERVERFIGLIO INTEGER;
  SER_RAMFIGLIO RAM.CAPACITA%TYPE;
  SER_COREFIGLIO PROCESSORE.CORE%TYPE;
  
  CAPA_HDD_SERVERTOT INTEGER;
  SER_RAMTOT RAM.CAPACITA%TYPE;
  SER_CORETOT PROCESSORE.CORE%TYPE;
  
  RAMVM VMCREATA.RAM%TYPE;
  PROCESSOREVM VMCREATA.PROCESSORE%TYPE;
  HDDVM VMCREATA.HDD%TYPE;

  CURSOR C IS SELECT ID FROM SERVER;
  SERV_ID SERVER.ID%TYPE;
  
  CURSOR D IS SELECT ID FROM SERVER WHERE ID > SERV_ID;
  SERV_IDFIGLIO SERVER.ID%TYPE;

BEGIN
  -- ASSEGNA UN SINGOLO SERVER
  TROVATO := 0;
  
  SELECT RAM INTO RAMVM FROM VMCREATA WHERE ID = IDVM;
  SELECT PROCESSORE INTO  PROCESSOREVM FROM VMCREATA WHERE ID = IDVM;
  SELECT  HDD INTO  HDDVM FROM VMCREATA WHERE ID = IDVM;



  OPEN C;
  LOOP
    FETCH C INTO serv_id;
    EXIT WHEN C%NOTFOUND;
      
      SELECT COUNT(DEREF(NT.VM).ID) INTO NUM_VM FROM TABLE(
        SELECT MACCHINEVIRTUALI FROM SERVER 
        WHERE ID = SERV_ID)NT;
      
      IF NUM_VM < 3 THEN
      
        SELECT SUM(DEREF(NT.HDD).CAPACITA) INTO CAPA_HDD_SERVER FROM TABLE(SELECT HDD FROM SERVER WHERE ID = serv_id)NT;
        SELECT DEREF(RAM).CAPACITA INTO SER_RAM FROM SERVER WHERE ID = serv_id;  
        SELECT DEREF(PROCESSORE).CORE INTO SER_CORE FROM SERVER WHERE ID = serv_id;
        
        
        IF (SER_RAM >= RAMVM AND 
           SER_CORE >= PROCESSOREVM AND
           CAPA_HDD_SERVER >= HDDVM
           ) THEN
          TROVATO := 1;
          
          AGGIORNA_SERVER_VM(SERV_ID, IDVM, 1);
          
          select count(deref(mt.vm).id) into NUM_VMCHECK from table(
            select macchinevirtuali from server where id = SERV_ID)mt;
          
          if NUM_VMCHECK = 0 then
            update server
            set macchinevirtuali = vmnt(ref_vmty((select ref(v) from vmcreata v where v.id = IDVM)))
            where id = serv_id;
          else
            update server
            set macchinevirtuali = macchinevirtuali
              multiset union all
              vmnt(ref_vmty((select ref(v) from vmcreata v where v.id = IDVM)))
            where id = serv_id;
          end if;
        END IF;
      
      END IF;
      
  END LOOP;
  CLOSE C;
  
  -- ASSEGNA PIU' SERVER
  IF TROVATO = 0 THEN
    OPEN C;
    LOOP
      FETCH C INTO SERV_ID;
      EXIT WHEN C%NOTFOUND;
      
      SELECT COUNT(DEREF(NT.VM).ID) INTO NUM_VM FROM TABLE(
        SELECT MACCHINEVIRTUALI FROM SERVER 
        WHERE ID = SERV_ID)NT;
      
      IF NUM_VM < 3 THEN
    
        SELECT SUM(DEREF(NT.HDD).CAPACITA) INTO CAPA_HDD_SERVER FROM TABLE(SELECT HDD FROM SERVER WHERE ID = SERV_ID)NT;
        SELECT DEREF(RAM).CAPACITA INTO SER_RAM FROM SERVER WHERE ID = SERV_ID;  
        SELECT DEREF(PROCESSORE).CORE INTO SER_CORE FROM SERVER WHERE ID = SERV_ID;
        
        OPEN D;
        LOOP
          FETCH D INTO SERV_IDFIGLIO;
          EXIT WHEN D%NOTFOUND;
            SELECT COUNT(DEREF(NT.VM).ID) INTO NUM_VMFIGLIO FROM TABLE(
              SELECT MACCHINEVIRTUALI FROM SERVER 
              WHERE ID = SERV_IDFIGLIO)NT;
            
            IF NUM_VMFIGLIO < 3 THEN
              SELECT SUM(DEREF(NT.HDD).CAPACITA) INTO CAPA_HDD_SERVERFIGLIO FROM TABLE(SELECT HDD FROM SERVER WHERE ID = SERV_IDFIGLIO)NT;
              SELECT DEREF(RAM).CAPACITA INTO SER_RAMFIGLIO FROM SERVER WHERE ID = SERV_IDFIGLIO;  
              SELECT DEREF(PROCESSORE).CORE INTO SER_COREFIGLIO FROM SERVER WHERE ID = SERV_IDFIGLIO;
              
              CAPA_HDD_SERVERTOT := CAPA_HDD_SERVER + CAPA_HDD_SERVERFIGLIO;
              SER_RAMTOT := SER_RAM + SER_RAMFIGLIO;
              SER_CORETOT := SER_CORE + SER_COREFIGLIO;
              
              IF (CAPA_HDD_SERVERTOT >= HDDVM AND
                  SER_RAMTOT >= RAMVM AND
                  SER_CORETOT >= PROCESSOREVM) THEN
              
                TROVATO := 1;
                
                AGGIORNA_SERVER_VM(SERV_ID, IDVM, 1);
                AGGIORNA_SERVER_VM(SERV_IDFIGLIO, IDVM, 2);
                
                select count(deref(mt.vm).id) into NUM_VMCHECK from table(
                  select macchinevirtuali from server where id = SERV_ID)mt;
                
                if NUM_VMCHECK = 0 then
                  update server
                  set macchinevirtuali = vmnt(ref_vmty((select ref(v) from vmcreata v where v.id = IDVM)))
                  where id = serv_id;
                else
                  update server
                  set macchinevirtuali = macchinevirtuali
                    multiset union all
                    vmnt(ref_vmty((select ref(v) from vmcreata v where v.id = IDVM)))
                  where id = serv_id;
                end if;
                
                select count(deref(mt.vm).id) into NUM_VMCHECK from table(
                  select macchinevirtuali from server where id = SERV_IDFIGLIO)mt;
                
                if NUM_VMCHECK = 0 then
                  update server
                  set macchinevirtuali = vmnt(ref_vmty((select ref(v) from vmcreata v where v.id = IDVM)))
                  where id = serv_idFIGLIO;
                else
                  update server
                  set macchinevirtuali = macchinevirtuali
                    multiset union all
                    vmnt(ref_vmty((select ref(v) from vmcreata v where v.id = IDVM)))
                  where id = serv_idFIGLIO;
                end if;
                
              END IF;
              
            END IF;
            
        END LOOP;
        CLOSE D;
      END IF;
      
    END LOOP;
    CLOSE C;
  END IF;
  
  -- SE NON ESISTONO SERVER
  IF TROVATO = 0 THEN
    dbms_output.put_line('Attenzione: inserimento annullato! Nessun server disponibile con quelle caratteristiche.');
    raise_application_error(-20000, 'Attenzione: inserimento annullato! Nessun server disponibile con quelle caratteristiche.');
  END IF;
END ASSEGNA_SERVER;
/

create or replace
PROCEDURE CREA_VM
( HDD IN HDD.CAPACITA%TYPE
, RAM IN RAM.CAPACITA%TYPE
, PROCESSORE IN PROCESSORE.CORE%TYPE
, SISTEMAOPERATIVO IN OS.NOME%TYPE
, CODFIS IN CLIENTE.CF%TYPE
) AS
IDVM VMCREATA.ID%TYPE;
COSTOOS OS.COSTO%TYPE;
NUM_VM INTEGER;

BEGIN
  SELECT COSTO INTO COSTOOS FROM OS WHERE NOME = SISTEMAOPERATIVO;
  
  insert into vmcreata values(null, 
    HDD, 
    RAM, 
    PROCESSORE, 
    (SELECT SYSTIMESTAMP FROM DUAL), 
    (SELECT COSTO FROM OS S WHERE S.NOME = SISTEMAOPERATIVO),
    NULL,
    NULL, 
    (SELECT REF(C) FROM CLIENTE C WHERE C.CF = CODFIS), 
    null);
    
    SELECT MAX(ID) INTO IDVM FROM VMCREATA;
    
    UPDATE VMCREATA
    SET SOFTWARE = SOFTWARENT(REF_SOFTWARETY((SELECT REF(S) FROM OS S WHERE S.NOME = SISTEMAOPERATIVO)))
    WHERE ID = IDVM;
    
    ASSEGNA_SERVER(IDVM);
    
    SELECT COUNT(DEREF(NT.VM).ID) INTO NUM_VM FROM TABLE(
    SELECT VM FROM CLIENTE 
    WHERE CF = CODFIS)NT;
    
    if NUM_VM = 0 then
      update CLIENTE
      set VM = vmnt(ref_vmty((select ref(v) from vmcreata v where v.id = IDVM)))
      where CF = CODFIS;
    else
      update CLIENTE
      set VM = VM
        multiset union all 
        vmnt(ref_vmty((select ref(v) from vmcreata v where v.id = IDVM)))
      where  CF = CODFIS;
    end if;
    
END CREA_VM;
/

-- INDICI
create index index_data on fattura (dataemissione);