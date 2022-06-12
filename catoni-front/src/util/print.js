export const printResourcesInHand = (resources, selectResource) => {
  const resSet = Array.from(new Set(resources))
  const map = resSet.map((r) => {
    return (
      <>
        <img
          width={62.5}
          height={90.5}
          src={`./${r.toLowerCase()}.jpg`}
          alt={`${r.toLowerCase()}`}
          onClick={() => { selectResource(r)}}
        />{" "}
        X{" "}
        {resources.reduce((acc, curr) => {
          return acc[curr] ? ++acc[curr] : (acc[curr] = 1), acc;
        }, {})[r]}
      </>
    );
  });

  return map;
};
