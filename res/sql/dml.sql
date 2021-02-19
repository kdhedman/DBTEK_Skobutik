-- Lista antalet produkter per kategori.
select kategori.kategori, count(skomodell.märkeid) as 'antal i kategorin'
from skomodell
         right join kategori on skomodell.märkeid=kategori.id
group by kategori;

-- Skapa en kundlista med den totala summan för varje kund.
select namn, sum(pris*kvantitet) as 'Total summa'
from kund
         join leverans on kund.id=leverans.Kundid
         join beställning on leverans.id=beställning.leveransID
         join skomodell on beställning.skomodellID=skomodell.id
group by kund.namn;

-- Vilka kunder har köpt sandaler i storlek 38 av Ecco.
select namn
from kund
         join leverans on kund.id=leverans.Kundid
         join beställning on leverans.id=beställning.leveransID
         join skomodell on beställning.skomodellID=skomodell.id
         join storlek on storlek.id=beställning.storlekID
         join färg on beställning.färgid=färg.id
where skomodell.skomodell = 'Ecco'
  and storlek.Skostorlek=38
  and färg.färg='svart'
group by kund.namn;

-- Skapa en topp-5 lista av de mest sålda produkterna.
select skomodell as 'TOP5 modeller',
       kvantitet as 'Antal sålda!'
from skomodell
         join beställning on skomodell.id = beställning.skomodellID;
order by kvantitet desc limit 5;

-- Vilken månad hade duden största försäljningen?
select extract(month from datum), sum(pris*kvantitet)
from kund
         join leverans on kund.id=leverans.Kundid
         join beställning on leverans.id=beställning.leveransID
         join skomodell on beställning.skomodellID=skomodell.id
group by extract(month from datum);

-- Skriv ut en lista på det totala beställningsvärdetper ort där beställningsvärdet är större än 1000 kr.
select sum(pris*kvantitet) as total, ort.namn
from skomodell
         join beställning on skomodell.id= beställning.skomodellID
         join leverans on leverans.id=beställning.leveransID
         join kund on leverans.Kundid=kund.id
         join ort on kund.ortid = ort.id
     -- where pris*kvantitet > 1000
group by ort.namn;