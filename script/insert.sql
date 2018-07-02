-- INSERIMENTI
insert into ram values(null, 4);
insert into ram values(null, 8);
insert into ram values(null, 16);
insert into ram values(null, 32);
insert into ram values(null, 64);
insert into ram values(null, 128);

insert into hdd values(null, 128);
insert into hdd values(null, 256);
insert into hdd values(null, 512);
insert into hdd values(null, 1024);
insert into hdd values(null, 2048);
insert into hdd values(null, 4096);

insert into processore values('Intel i3', 4);
insert into processore values('Intel i3', 6);
insert into processore values('Intel i3', 8);
insert into processore values('Intel i5', 4);
insert into processore values('Intel i5', 6);
insert into processore values('Intel i5', 8);
insert into processore values('Intel i7', 4);
insert into processore values('Intel i7', 6);
insert into processore values('Intel i7', 8);
insert into processore values('AND', 4);
insert into processore values('AMD', 6);
insert into processore values('AMD', 8);

INSERT INTO SERVER VALUES(
  null, 
  ((select ref(r) from ram r where id = 1)), 
  ((select ref(p) from processore p where modello='Intel i3' and core=4)),
  null,
  null
);
/

update server
set hdd = cast(multiset(select ref(h) from hdd h where h.id = 1)as hddnt)
where id=1;
/

INSERT INTO SERVER VALUES(
  null, 
  ((select ref(r) from ram r where id = 4)), 
  ((select ref(p) from processore p where modello='Intel i5' and core=6)),
  null,
  null
);
/

update server
set hdd = cast(multiset(select ref(h) from hdd h where h.capacita < 300)as hddnt)
where id=2;
/

INSERT INTO SERVER VALUES(
  null, 
  ((select ref(r) from ram r where id = 6)), 
  ((select ref(p) from processore p where modello='Intel i7' and core=8)),
  null,
  null
);
/

update server
set hdd = cast(multiset(select ref(h) from hdd h where h.capacita > 2000)as hddnt)
where id=3;
/

insert into os values(null, 'Windows 10', 13.00, null);
insert into os values(null, 'Windows 7', 5.00, null);
insert into os values(null, 'MAC OS X', 25.00, null);
insert into os values(null, 'Linux Mint 19', 1.00, null);
insert into os values(null, 'Ubuntu 18.04', 1.00, null);

insert into app values(null, 'Sql Developer', 30.00, 'professional',11.2);
insert into app values(null, 'Builder center', 20.00, 'base',11.2);
insert into app values(null, 'Visual paradigm', 40.00, 'base',11.2);
insert into app values(null, 'Eclipse', 5.00, 'free trial',11.2);
insert into app values(null, 'Web Storm', 15.00, 'free trial',11.2);

update os set app = cast(multiset(select ref(a) from app a where a.id > 0)as appnt);
/

-- UTENTI
insert into users values('admin@mail.com', 'admin', 'A', null);

insert into cliente values('cchfrz91b01d643j', 'Fabrizio', 'Occhionero', 'fabrizio@mail.com', 'Foggia', 0, null, null);
insert into cliente values('rrtstn91b01d643j', 'Roberto', 'Stanziale', 'roberto@mail.com', 'Foggia', 0, null, null);
insert into users values('fabrizio@mail.com', 'fabrizio', 'U', ((select ref(c) from cliente c where c.nome = 'Fabrizio')));
insert into users values('roberto@mail.com', 'roberto', 'U', ((select ref(c) from cliente c where c.nome = 'Roberto')));

-- MACCHINE VIRTUALI
BEGIN
  crea_vm(256, 32, 8, 'Windows 10', 'cchfrz91b01d643j');
  crea_vm(128, 16, 6, 'Windows 7', 'cchfrz91b01d643j');
  crea_vm(256, 16, 8, 'Linux Mint 19', 'rrtstn91b01d643j');
END;

-- APPLICAZIONI
BEGIN
  aggiungi_applicazione_vm('Sql Developer', 1);
  aggiungi_applicazione_vm('Builder center', 1);
  aggiungi_applicazione_vm('Visual paradigm', 2);
  aggiungi_applicazione_vm('Eclipse', 3);
  aggiungi_applicazione_vm('Web Storm', 3);
END;
