function ArmyRules({ rules }) {
    if (!rules || rules.length === 0) {
        return null;
    }

    return (
        <details className="panel army-rules">
            <summary>Army rules: {rules.map(rule => rule.name).join(", ")}</summary>
            <dl className="mb-0 mt-2">
                {rules.map(rule => (
                    <div key={rule.name} className="army-rule">
                        <dt>{rule.name}</dt>
                        <dd>{rule.description}</dd>
                    </div>
                ))}
            </dl>
        </details>
    );
}

export default ArmyRules;
