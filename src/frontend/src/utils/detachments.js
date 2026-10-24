export function withDetachments(units) {
    return units
        .filter(unit => unit.parentId == null)
        .flatMap(unit => [unit, ...units.filter(detachment => detachment.parentId === unit.id)]);
}
