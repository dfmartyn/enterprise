CREATE TABLE hierarchy (
  id     SERIAL PRIMARY KEY,
  name   VARCHAR(50) UNIQUE NOT NULL,
  parent INTEGER REFERENCES hierarchy (id)
);

INSERT INTO public.hierarchy (id, name, parent) VALUES (1, 'Дмитрий', NULL);
INSERT INTO public.hierarchy (id, name, parent) VALUES (4, 'Захар', 3);
INSERT INTO public.hierarchy (id, name, parent) VALUES (3, 'Иван', 2);
INSERT INTO public.hierarchy (id, name, parent) VALUES (2, 'Василий', 1);

WITH RECURSIVE search_graph AS (
  SELECT
    id,
    name,
    parent
  FROM hierarchy
  WHERE parent = 3
  UNION ALL
  SELECT
    h.id,
    h.name,
    h.parent
  FROM search_graph sg JOIN hierarchy h ON sg.id = h.parent
)
SELECT *
FROM search_graph;