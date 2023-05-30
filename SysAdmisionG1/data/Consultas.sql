
INSERT INTO area_periodo_result(id_area_periodo, id_area_examen, porcentaje) VALUES(1,2,0.20);

select p.*, ae.nombreae, apx.nombre as periodo
from area_periodo_result p, area_examen ae, 
(SELECT ap.id_area_periodo, pp.nombre from area_periodo ap, periodo pp where ap.id_periodo=pp.id_periodo) apx 
where p.id_area_examen=ae.id_area_examen and p.id_area_periodo=apx.id_area_periodo;

SELECT ap.id_area_periodo, pp.nombre from area_periodo ap, periodo pp where ap.id_periodo=pp.id_periodo;
