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