<script>
    import Bar from 'components/Bar.svelte';
    import Machine from 'components/Machine.svelte';

    export let context = {};

    let creating = false;
    let customerUuid = null;
    let domainUuid = null;
    let fetched = false;
    let hasMachines = false;
    let machineUuid = null;
    let siteUuid = null;
    let machines = [];

    const updatedMachine = () => {
        if (context.domain == null) {
            return false;
        }

        if (domainUuid == null) {
            return true;
        }

        return (
            (machineUuid && context.machines) &&
            (context.machines.length > 0) &&
            (context.machines[context.machines.length-1].uuid != machineUuid))
    };

    $: if (updatedMachine()) {
        machines = [];
        customerUuid = context.customer.uuid;
        domainUuid = context.domain.uuid;
        machineUuid = context.machines[context.machines.length-1].uuid;
        siteUuid = context.site.uuid;

        fetch(`/api/v1/domains/${domainUuid}/customers/${customerUuid}/sites/${siteUuid}/route/${machineUuid}`)
            .then((response) => response.json())
            .then((result) => {
                fetched = true;
                if (result) {
                    for (let pair of result) {
                        if (pair.node.parentUuid == machineUuid) {
                            machines.push(pair.node);
                        }
                    }

                    hasMachines = (machines.length > 0);
                    machines = machines;
                }
            }).catch((err) => console.log('Failed to fetch any sub-machines on route!'));
    }

    const create = () => {
        machines = [{}, ...machines];
        creating = true;
        hasMachines = true;
    };

    const onCancel = () => {
        machines.splice(0, 1);
        machines = machines;
        hasMachines = (machines && (machines.length > 0));
        creating = false;
    }

    const onOK = (name, location) => {
        const body = JSON.stringify({location, name});
        const headers = {'Content-Type': 'application/json'};
        const method = 'POST';
        
        fetch(`/api/v1/domains/${domainUuid}/customers/${customerUuid}/sites/${siteUuid}/route/${machineUuid}`, {body, headers, method})
            .then((response) => response.json())
            .then((result) => {
                machines[0].location = result.location;
                machines[0].name = result.name;
                machines[0].uuid = result.uuid;
                machines = machines;
                creating = false;
            }).catch((err) => alert('Failed to append machine to route!'));
    };
</script>

<component>
    {#if fetched}
    <Bar {create} {creating} itemClass="Machine"/>
    {#if hasMachines}
    <items>
        {#each machines as machine}
        <Machine on:refresh {context} {machine} {onCancel} {onOK}/>
        {/each}
    </items>
    {/if}
    {/if}
</component>
